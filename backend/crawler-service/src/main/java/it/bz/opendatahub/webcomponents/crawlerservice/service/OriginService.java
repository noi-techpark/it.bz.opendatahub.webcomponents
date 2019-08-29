package it.bz.opendatahub.webcomponents.crawlerservice.service;

import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;

import java.util.List;

public interface OriginService {
    List<OriginModel> listAllOrigins();

    List<OriginModel> listAllOrigins(boolean deleted);

    void updateOrigin(OriginModel origin);

    void cascadeDeletedOrigins();
}
