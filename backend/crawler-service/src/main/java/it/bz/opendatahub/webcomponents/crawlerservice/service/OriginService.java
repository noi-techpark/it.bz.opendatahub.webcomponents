package it.bz.opendatahub.webcomponents.crawlerservice.service;

public interface OriginService {
    /**
     * checkForChanges
     * updateMasterList
     * importChanges
     */

    boolean isUpToDate();

    void update();
}
