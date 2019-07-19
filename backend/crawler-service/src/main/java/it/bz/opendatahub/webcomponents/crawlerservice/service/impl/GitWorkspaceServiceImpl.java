package it.bz.opendatahub.webcomponents.crawlerservice.service.impl;

import it.bz.opendatahub.webcomponents.crawlerservice.config.WorkspaceConfiguration;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRemote;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.impl.GithubApiRepository;
import it.bz.opendatahub.webcomponents.crawlerservice.service.GitWorkspaceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class GitWorkspaceServiceImpl implements GitWorkspaceService {
    private WorkspaceConfiguration workspaceConfiguration;

    @Autowired
    public GitWorkspaceServiceImpl(WorkspaceConfiguration workspaceConfiguration) {
        this.workspaceConfiguration = workspaceConfiguration;
    }

    @Override
    public void checkoutHeadOfBranch(String localRepositoryIdentifier, String branchName) {
        Path localPath = Paths.get(workspaceConfiguration.getPath(), localRepositoryIdentifier);

        String repoUrl = "https://github.com/uhufa/odh-web-components-store-demo-origins.git";

        cloneRepository(repoUrl, localPath);
    }

    @Override
    public void checkoutRevisionAtTag(GitRemote remote, GithubApiRepository.TagEntry tag) {
        try  (Git git = Git.open(getLocalPathForRemote(remote).toFile())) {
            git.checkout().setName(tag.getCommitSha()).call();
        } catch (GitAPIException | IOException e) {
            log.error("Exception occurred while cloning repo", e);
        }
    }

    @Override
    public void updateRemote(GitRemote remote) {
        Path localPath = getLocalPathForRemote(remote);

        if(!Files.exists(localPath)) {
            cloneRepository(remote.getUrl(), localPath);
        }
        else {
            fetchRepository(localPath);
        }
    }

    @Override
    public byte[] readFile(String localRepositoryIdentifier, String pathToFile) {
        Path localPath = Paths.get(workspaceConfiguration.getPath(), localRepositoryIdentifier, pathToFile);

        try {
            return FileUtils.readFileToByteArray(localPath.toFile());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getLocalPathForRemote(GitRemote remote) {
        return Paths.get(workspaceConfiguration.getPath(), remote.getUuid());
    }

    private void cloneRepository(String repoUrl, Path cloneToPath) {
        try {
            FileUtils.deleteDirectory(cloneToPath.toFile());
        }
        catch (IOException e) {
            log.error("delete error", e);
            // ignore since delete is optional here
        }

        try {
            log.debug("Cloning "+repoUrl+" into "+cloneToPath.toString());
            Git git = Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(cloneToPath.toFile())
                    .call();

            git.close();
            log.debug("Completed Cloning");
        } catch (GitAPIException e) {
            log.error("Exception occurred while cloning repo", e);
        }
    }

    private void fetchRepository(Path localPath) {
        try  (Git git = Git.open(localPath.toFile())) {
            git.fetch().call();
        } catch (GitAPIException | IOException e) {
            log.error("Exception occurred while cloning repo", e);
        }
    }
}
