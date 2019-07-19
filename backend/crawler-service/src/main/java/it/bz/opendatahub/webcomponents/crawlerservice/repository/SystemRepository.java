package it.bz.opendatahub.webcomponents.crawlerservice.repository;

public interface SystemRepository {
    String getHeadCommitHashForOrigin();

    void setHeadCommitHashForOrigin(String commitHash);
}
