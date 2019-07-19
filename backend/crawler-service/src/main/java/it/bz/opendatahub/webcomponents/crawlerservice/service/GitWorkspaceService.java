package it.bz.opendatahub.webcomponents.crawlerservice.service;

import it.bz.opendatahub.webcomponents.crawlerservice.data.api.Origin;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.GitRemote;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.impl.GithubApiRepository;

public interface GitWorkspaceService {
    void checkoutHeadOfBranch(String localRepositoryIdentifier, String branchName);

    void checkoutRevisionAtTag(GitRemote remote, GithubApiRepository.TagEntry tag);

    void updateRemote(GitRemote remote);

    byte[] readFile(String localRepositoryIdentifier, String pathToFile);
}
