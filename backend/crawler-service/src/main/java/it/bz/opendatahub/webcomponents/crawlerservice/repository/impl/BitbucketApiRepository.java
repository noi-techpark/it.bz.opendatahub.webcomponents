package it.bz.opendatahub.webcomponents.crawlerservice.repository.impl;

import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BitbucketApiRepository implements VcsApiRepository {
    @Override
    public List<GithubApiRepository.TagEntry> listVersions(String repoUrl) {
        return null;
    }

    @Override
    public String getLatestCommitHash(String repoUrl, String branch) {
        return null;
    }

    @Override
    public byte[] getFileContentsForCommitHash(String repoUrl, String commitHash, String pathToFile) {
        return new byte[0];
    }
}
