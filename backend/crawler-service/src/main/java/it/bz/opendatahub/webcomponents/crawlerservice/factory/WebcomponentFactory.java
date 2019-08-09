package it.bz.opendatahub.webcomponents.crawlerservice.factory;

import it.bz.opendatahub.webcomponents.crawlerservice.data.api.Manifest;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.WebcomponentModel;
import org.springframework.stereotype.Component;

@Component
public class WebcomponentFactory {
    public WebcomponentModel createFromManifest(String uuid, Manifest manifest) {
        WebcomponentModel newEntry = new WebcomponentModel();

        newEntry.setUuid(uuid);
        newEntry.setTitle(manifest.getTitle());
        newEntry.setDescription(manifest.getDescription());
        newEntry.setDescriptionAbstract(manifest.getDescriptionAbstract());
        newEntry.setLicense(manifest.getLicense());

        return newEntry;
    }
}
