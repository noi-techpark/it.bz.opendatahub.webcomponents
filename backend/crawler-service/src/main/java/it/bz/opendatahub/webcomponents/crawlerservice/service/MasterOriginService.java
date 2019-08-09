package it.bz.opendatahub.webcomponents.crawlerservice.service;

public interface MasterOriginService {
    /**
     * checkForChanges
     * updateMasterList
     * importChanges
     */

    boolean isUpToDate();

    void update();
}
