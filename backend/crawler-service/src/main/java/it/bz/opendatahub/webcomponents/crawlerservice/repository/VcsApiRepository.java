package it.bz.opendatahub.webcomponents.crawlerservice.repository;

import it.bz.opendatahub.webcomponents.crawlerservice.repository.impl.GithubApiRepository;

import java.util.List;

public interface VcsApiRepository {
    List<GithubApiRepository.TagEntry> listVersions(String repoUrl);

    String getLatestCommitHash(String repoUrl, String branch);

    byte[] getFileContentsForCommitHash(String repoUrl, String commitHash, String pathToFile);
}