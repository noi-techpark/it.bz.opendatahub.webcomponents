package it.bz.opendatahub.webcomponents.crawlerservice.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.crawlerservice.data.api.Manifest;
import it.bz.opendatahub.webcomponents.crawlerservice.data.api.Origin;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRemote;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.OriginSystemEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.OriginRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentVersionRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.impl.GithubApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.GitWorkspaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class ComponentUpdateSchedule {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private OriginRepository originRepository;
    private VcsApiRepository vcsApiRepository;

    private WebcomponentRepository webcomponentRepository;
    private WebcomponentVersionRepository webcomponentVersionRepository;

    private GitWorkspaceService gitWorkspaceService;

    @Autowired
    public ComponentUpdateSchedule(@Qualifier("githubApiRepository") VcsApiRepository vcsApiRepository,
                                   OriginRepository originRepository,
                                   WebcomponentRepository webcomponentRepository,
                                   GitWorkspaceService gitWorkspaceService,
                                   WebcomponentVersionRepository webcomponentVersionRepository) {

        this.originRepository = originRepository;
        this.vcsApiRepository = vcsApiRepository;

        this.webcomponentRepository = webcomponentRepository;
        this.webcomponentVersionRepository = webcomponentVersionRepository;

        this.gitWorkspaceService = gitWorkspaceService;
    }

    @Scheduled(fixedDelayString = "${application.schedule.component}")
    public void updateComponents() {
        log.info("comps update");

        List<OriginModel> originModelList = originRepository.findAll();

        for(OriginModel origin : originModelList) {
            updateOrigin(origin);
        }
    }

    private void updateOrigin(OriginModel origin) {
        List<GithubApiRepository.TagEntry> versions = vcsApiRepository.listVersions(origin.getUrl());

        log.info(versions.toString());

        if(!versions.isEmpty()) {
            gitWorkspaceService.updateRemote(new GitRemote(origin.getUuid(), origin.getUrl(), origin.getApi()));

            gitWorkspaceService.checkoutRevisionAtTag(new GitRemote(origin.getUuid(), origin.getUrl(), origin.getApi()), getLatestVersion(versions));

            byte[] originsFileData = gitWorkspaceService.readFile(origin.getUuid(), "wcs-manifest.json");

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
                WebcomponentModel newEntry = new WebcomponentModel();

                newEntry.setUuid(origin.getUuid());
                newEntry.setTitle(manifest.getTitle());
                newEntry.setDescription(manifest.getDescription());
                newEntry.setDescriptionAbstract(manifest.getDescriptionAbstract());
                newEntry.setLicense(manifest.getLicense());

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

        Collections.sort(versions, (entryA, entryB) -> entryA.getName().compareToIgnoreCase(entryB.getName()));

        return versions.get(0);
    }

    private void updateVersion(WebcomponentModel webcomponent, GithubApiRepository.TagEntry version) {
        log.debug("processing version: "+version.getName());
        Optional<WebcomponentVersionModel> probe = webcomponentVersionRepository.findByUuidAndTag(webcomponent.getUuid(), version.getName());

        if(probe.isEmpty()) {
            WebcomponentVersionModel newEntry = new WebcomponentVersionModel();
            newEntry.setWebcomponentUuid(webcomponent.getUuid());
            newEntry.setVersionTag(version.getName());
            newEntry.setReleaseTimestamp(new Date());

            webcomponentVersionRepository.save(newEntry);
        }
    }
}
