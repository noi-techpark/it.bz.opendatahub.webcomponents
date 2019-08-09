package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.crawlerservice.data.api.Manifest;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.crawlerservice.factory.WebcomponentFactory;
import it.bz.opendatahub.webcomponents.crawlerservice.factory.WebcomponentVersionFactory;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.OriginRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentVersionRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.impl.GithubApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WebcomponentServiceImpl implements WebcomponentService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private VcsApiRepository vcsApiRepository;

    private WebcomponentRepository webcomponentRepository;
    private WebcomponentVersionRepository webcomponentVersionRepository;

    private WebcomponentFactory webcomponentFactory;
    private WebcomponentVersionFactory webcomponentVersionFactory;

    @Autowired
    public WebcomponentServiceImpl(@Qualifier("githubApiRepository") VcsApiRepository vcsApiRepository,
                                      WebcomponentRepository webcomponentRepository,
                                      WebcomponentVersionRepository webcomponentVersionRepository,
                                      WebcomponentFactory webcomponentFactory,
                                      WebcomponentVersionFactory webcomponentVersionFactory) {

        this.vcsApiRepository = vcsApiRepository;

        this.webcomponentRepository = webcomponentRepository;
        this.webcomponentVersionRepository = webcomponentVersionRepository;

        this.webcomponentFactory = webcomponentFactory;
        this.webcomponentVersionFactory = webcomponentVersionFactory;
    }

    @Override
    public void updateOrigin(OriginModel origin) {
        List<GithubApiRepository.TagEntry> versions = vcsApiRepository.listVersions(origin.getUrl());

        log.info(versions.toString());

        if(!versions.isEmpty()) {
            byte[] originsFileData = vcsApiRepository.getFileContentsForCommitHash(origin.getUrl(), getLatestVersion(versions).getCommitSha(), "wcs-manifest.json");

            Manifest manifest;
            try {
                manifest = objectMapper.readValue(originsFileData, Manifest.class);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

            //TODO: upsert main entry
            WebcomponentModel entry;
            Optional<WebcomponentModel> probe = webcomponentRepository.findById(origin.getUuid());
            if(!probe.isPresent()) {
                WebcomponentModel newEntry = webcomponentFactory.createFromManifest(origin.getUuid(), manifest);

                entry = webcomponentRepository.save(newEntry);
            }
            else {
                entry = probe.get();
            }

            for(GithubApiRepository.TagEntry version : versions) {
                updateVersion(entry, version);
            }
        }
        else {
            log.warn("no versions found for: "+origin.getUrl());
        }
    }

    private GithubApiRepository.TagEntry getLatestVersion(List<GithubApiRepository.TagEntry> versions) {
        if(versions.isEmpty()) {
            throw new RuntimeException("empty list");
        }

        versions.sort((entryA, entryB) -> entryA.getName().compareToIgnoreCase(entryB.getName()));

        return versions.get(0);
    }

    private void updateVersion(WebcomponentModel webcomponent, GithubApiRepository.TagEntry version) {
        log.debug("processing version: "+version.getName());
        Optional<WebcomponentVersionModel> probe = webcomponentVersionRepository.findByUuidAndTag(webcomponent.getUuid(), version.getName());

        if(!probe.isPresent()) {
            WebcomponentVersionModel newEntry = webcomponentVersionFactory.createFromTagEntry(webcomponent.getUuid(), version);

            webcomponentVersionRepository.save(newEntry);
        }
    }
}
