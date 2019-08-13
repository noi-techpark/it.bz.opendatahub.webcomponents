package it.bz.opendatahub.webcomponents.crawlerservice.repository;

public interface SystemRepository {
    String getHeadOfMasterOrigin();

    void setHeadOfMasterOrigin(String commitHash);
}
