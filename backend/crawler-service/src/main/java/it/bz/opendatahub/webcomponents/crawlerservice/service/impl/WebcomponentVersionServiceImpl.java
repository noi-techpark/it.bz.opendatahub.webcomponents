package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.common.data.model.id.WebcomponentVersionId;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.Manifest;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.CommitEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRemote;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRevision;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.TagEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.CrawlerException;
import it.bz.opendatahub.webcomponents.crawlerservice.exception.NotFoundException;
import it.bz.opendatahub.webcomponents.crawlerservice.factory.WebcomponentVersionFactory;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WebcomponentVersionRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.WorkspaceRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.WebcomponentVersionService;
import lombok.extern.slf4j.Slf4j;
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
public class WebcomponentVersionServiceImpl implements WebcomponentVersionService {
    private static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String MANIFEST_FILE_NAME = "wcs-manifest.json";

    private final VcsApiRepository vcsApiRepository;

    private final WebcomponentVersionRepository webcomponentVersionRepository;
    private final WorkspaceRepository workspaceRepository;

    private final WebcomponentVersionFactory webcomponentVersionFactory;

    @Autowired
    public WebcomponentVersionServiceImpl(@Qualifier("githubApiRepository") final VcsApiRepository vcsApiRepository,
                                          final WebcomponentVersionRepository webcomponentVersionRepository,
                                          final WorkspaceRepository workspaceRepository,
                                          final WebcomponentVersionFactory webcomponentVersionFactory) {

        this.vcsApiRepository = vcsApiRepository;

        this.webcomponentVersionRepository = webcomponentVersionRepository;
        this.workspaceRepository = workspaceRepository;

        this.webcomponentVersionFactory = webcomponentVersionFactory;
    }

    @Override
    public void purgeVersionForTag(OriginModel origin, TagEntry tagEntry) {
        workspaceRepository.removeDirectory(Paths.get(origin.getUuid(), tagEntry.getName()));

        webcomponentVersionRepository.deleteById(WebcomponentVersionId.of(origin.getUuid(), tagEntry.getName()));
    }

    @Override
    public void createVersionFromTag(OriginModel origin, TagEntry tagEntry) {

        log.debug("createVersionFromTag for origin {} with tag {}", origin.getUrl(), tagEntry.getName());

        GitRevision gitRevision = GitRevision.of(GitRemote.of(origin), tagEntry);

        Manifest manifest;
        try {
            manifest = readManifestFromRemote(gitRevision);
        } catch (NotFoundException e) {
            return;
        }

        log.debug("...manifest found with title {}, image {} and dist {}", manifest.getTitle(), manifest.getImage(), manifest.getDist().getBasePath() + manifest.getDist().getFiles());

        Path versionBasePath = Paths.get(origin.getUuid(), tagEntry.getName());
        saveImage(gitRevision, manifest, versionBasePath);
        saveDist(gitRevision, manifest, versionBasePath);
        persistManifest(manifest, versionBasePath);
        upsertVersion(gitRevision, origin.getUuid(), manifest);
    }

    @Override
    public void cascadeDeletedTags() {
        webcomponentVersionRepository.markDeletedWhereTagIsDeleted();
    }

    @Override
    public WebcomponentVersionModel getLatestVersionForWebcomponent(String uuid) {
        List<WebcomponentVersionModel> versions = webcomponentVersionRepository.getLatestVersionForWebcomponent(uuid);

        if(versions.isEmpty()) {
            throw new NotFoundException("no version found");
        }

        return versions.get(0);
    }

    private void upsertVersion(GitRevision gitRevision, String originUuid, Manifest manifest) {
        Optional<WebcomponentVersionModel> probe = webcomponentVersionRepository.findById(WebcomponentVersionId.of(originUuid, gitRevision.getTagEntry().getName()));

        if(!probe.isPresent()) {
            WebcomponentVersionModel newEntry = webcomponentVersionFactory.createFromTagEntry(originUuid, gitRevision.getTagEntry(), manifest);

            CommitEntry commit = vcsApiRepository.getMetadataForCommit(gitRevision.getGitRemote(), gitRevision.getTagEntry().getRevisionHash());

            newEntry.setReleaseTimestamp(commit.getDate());

            webcomponentVersionRepository.save(newEntry);
        }
        else {
            WebcomponentVersionModel entry = probe.get();

            CommitEntry commit = vcsApiRepository.getMetadataForCommit(gitRevision.getGitRemote(), gitRevision.getTagEntry().getRevisionHash());

            entry.setReleaseTimestamp(commit.getDate());
            entry.setConfiguration(manifest.getConfiguration());
            entry.setDeleted(false);
            entry.setDist(manifest.getDist());

            webcomponentVersionRepository.save(entry);
        }
    }

    private void saveImage(GitRevision gitRevision, Manifest manifest, Path versionBasePath) {
        if(manifest.getImage() != null && !manifest.getImage().isEmpty()) {
            log.debug("...save image {}", manifest.getImage());
            persistImage(gitRevision, manifest, versionBasePath);
        }
    }

    private void persistImage(GitRevision gitRevision, Manifest manifest, Path versionBasePath) {
        try {
            ByteArrayOutputStream imageData = vcsApiRepository.getFileContents(gitRevision.getGitRemote(), gitRevision.getTagEntry().getRevisionHash(), manifest.getImage());
            if (imageData.size() > 0) {
                workspaceRepository.writeFile(imageData, Paths.get(versionBasePath.toString(), manifest.getImage()));
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

    private void persistManifest(Manifest manifest, Path path) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            objectMapper.writeValue(outputStream, manifest);
        }
        catch (IOException e) {
            throw new CrawlerException(e);
        }

        workspaceRepository.writeFile(outputStream, Paths.get(path.toString(), MANIFEST_FILE_NAME));
    }

    private void saveDist(GitRevision gitRevision, Manifest manifest, Path versionBasePath) {
        if(manifest.getDist() == null) {
            return;
        }

        log.debug("...save dist at base path {}", manifest.getDist().getBasePath());

        for(String file: manifest.getDist().getFiles()) {

            log.debug("......save file {}", file);
            ByteArrayOutputStream distFile = vcsApiRepository.getFileContents(gitRevision.getGitRemote(), gitRevision.getTagEntry().getRevisionHash(), manifest.getDist().getBasePath()+"/"+ file);

            Path path = Paths.get(versionBasePath.toString(), "dist", Paths.get(file).getFileName().toString());

            workspaceRepository.writeFile(distFile, path);
            log.debug("..........OK!");
        }
    }
}
