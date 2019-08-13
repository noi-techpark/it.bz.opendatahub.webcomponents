package it.bz.opendatahub.webcomponents.crawlerservice.factory;

import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.Origin;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import org.springframework.stereotype.Component;

@Component
public class OriginModelFactory {
    public OriginModel create(Origin mappedOrigin) {
        OriginModel originModel = new OriginModel();

        originModel.setUuid(mappedOrigin.getUuid());
        originModel.setApi(mappedOrigin.getApi());
        originModel.setUrl(mappedOrigin.getUrl());

        return originModel;
    }
}
