package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.Manifest;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRemote;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRevision;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.TagEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.CrawlerException;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.NotFoundException;
import it.bz.opendatahub.webcomponents.crawlerservice.factory.WebcomponentFactory;
import it.bz.opendatahub.webcomponents.crawlerservice.factory.WebcomponentVersionFactory;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentVersionRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WorkspaceRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WebcomponentServiceImpl implements WebcomponentService {
    private static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String MANIFEST_FILE_NAME = "wcs-manifest.json";

    private VcsApiRepository vcsApiRepository;

    private WebcomponentRepository webcomponentRepository;
    private WebcomponentVersionRepository webcomponentVersionRepository;

    private WebcomponentFactory webcomponentFactory;
    private WebcomponentVersionFactory webcomponentVersionFactory;

    private WorkspaceRepository workspaceRepository;

    @Autowired
    public WebcomponentServiceImpl(@Qualifier("githubApiRepository") VcsApiRepository vcsApiRepository,
                                   WebcomponentRepository webcomponentRepository,
                                   WebcomponentVersionRepository webcomponentVersionRepository,
                                   WebcomponentFactory webcomponentFactory,
                                   WebcomponentVersionFactory webcomponentVersionFactory,
                                   WorkspaceRepository workspaceRepository) {

        this.vcsApiRepository = vcsApiRepository;

        this.webcomponentRepository = webcomponentRepository;
        this.webcomponentVersionRepository = webcomponentVersionRepository;

        this.webcomponentFactory = webcomponentFactory;
        this.webcomponentVersionFactory = webcomponentVersionFactory;

        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public void updateWebcomponentFromOrigin(OriginModel origin) {
        GitRemote gitRemote = GitRemote.of(origin);

        List<TagEntry> versions = vcsApiRepository.listVersionTags(gitRemote);

        if(versions.isEmpty()) {
            log.warn("no versions found for: "+origin.getUrl());
            return;
        }

        GitRevision gitRevision = GitRevision.of(gitRemote, getLatestVersion(versions));

        Manifest manifest = readManifestFromRemote(gitRevision);

        persistManifest(manifest, origin.getUuid());

        if(manifest.getImage() != null && !manifest.getImage().isEmpty()) {
            persistImage(gitRevision, manifest, origin.getUuid());
        }

        persistWebcomponent(manifest, origin.getUuid());

        for(TagEntry version : versions) {
            updateVersion(GitRevision.of(gitRemote, version), origin.getUuid());
        }
    }

    private void persistWebcomponent(Manifest manifest, String originUuid) {
        WebcomponentModel newEntry = webcomponentFactory.createFromManifest(originUuid, manifest);

        WebcomponentModel entry;
        Optional<WebcomponentModel> probe = webcomponentRepository.findById(originUuid);
        if(probe.isPresent()) {
            entry = probe.get();

            BeanUtils.copyProperties(newEntry, entry);
        }
        else {
            entry = newEntry;
        }

        webcomponentRepository.save(entry);
    }

    private void persistImage(GitRevision gitRevision, Manifest manifest, String originUuid) {
        try {
            ByteArrayOutputStream imageData = vcsApiRepository.getFileContents(gitRevision.getGitRemote(), gitRevision.getTagEntry().getRevisionHash(), manifest.getImage());
            if (imageData.size() > 0) {
                workspaceRepository.writeFile(imageData, Paths.get(originUuid, "wcs-logo.png"));
            }
        }
        catch (NotFoundException e) {
            log.debug("image not found");
        }
    }

    private Manifest readManifestFromRemote(GitRevision gitRevision) {
        ByteArrayOutputStream originsFileData = vcsApiRepository.getFileContents(gitRevision.getGitRemote(), gitRevision.getTagEntry().getRevisionHash(), MANIFEST_FILE_NAME);

        Manifest manifest;
        try {
            manifest = objectMapper.readValue(originsFileData.toByteArray(), Manifest.class);
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }

        return manifest;
    }

    private void persistManifest(Manifest manifest, String originUuid) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            objectMapper.writeValue(outputStream, manifest);
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }

        workspaceRepository.writeFile(outputStream, Paths.get(originUuid, MANIFEST_FILE_NAME));
    }

    private TagEntry getLatestVersion(List<TagEntry> versions) {
        if(versions.isEmpty()) {
            throw new CrawlerException("empty list");
        }

        versions.sort((entryA, entryB) -> entryA.getName().compareToIgnoreCase(entryB.getName()));

        return versions.get(0);
    }

    private void updateVersion(GitRevision gitRevision, String originUuid) {
        log.debug("processing version: {}", gitRevision.getTagEntry().getName());

        Optional<WebcomponentVersionModel> probe = webcomponentVersionRepository.findByUuidAndTag(originUuid, gitRevision.getTagEntry().getName());

        if(!probe.isPresent()) {
            WebcomponentVersionModel newEntry = webcomponentVersionFactory.createFromTagEntry(originUuid, gitRevision.getTagEntry());

            webcomponentVersionRepository.save(newEntry);

            saveDist(gitRevision, originUuid);
        }
    }

    private void saveDist(GitRevision gitRevision, String originUuid) {
        //get manifest for this version

        ByteArrayOutputStream originsFileData = vcsApiRepository.getFileContents(gitRevision.getGitRemote(), gitRevision.getTagEntry().getRevisionHash(), MANIFEST_FILE_NAME);

        Manifest manifest;
        try {
            manifest = objectMapper.readValue(originsFileData.toByteArray(), Manifest.class);
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }

        //extract all dist files to disk

        extractAllFilesToDisk(gitRevision, Paths.get(originUuid, "dist", gitRevision.getTagEntry().getName()), manifest.getDist());
    }

    private void extractAllFilesToDisk(GitRevision gitRevision, Path basepath, List<String> files) {
        for(String file: files) {
            ByteArrayOutputStream distFile = vcsApiRepository.getFileContents(gitRevision.getGitRemote(), gitRevision.getTagEntry().getRevisionHash(), file);

            Path path = Paths.get(basepath.toString(), Paths.get(file).getFileName().toString());

            workspaceRepository.writeFile(distFile, path);
        }
    }
}
