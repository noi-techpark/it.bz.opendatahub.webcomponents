package it.bz.opendatahub.webcomponents.crawlerservice.factory;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.api.Manifest;
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
        newEntry.setAuthors(manifest.getAuthors());
        newEntry.setSearchTags(manifest.getSearchTags());

        return newEntry;
    }
}
