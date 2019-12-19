package it.bz.opendatahub.webcomponents.crawlerservice.factory;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.Manifest;
import org.springframework.stereotype.Component;

@Component
public class WebcomponentFactory {
    public WebcomponentModel createFromManifest(String uuid, Manifest manifest, String repositoryUrl) {
        WebcomponentModel newEntry = new WebcomponentModel();

        newEntry.setUuid(uuid);
        newEntry.setTitle(manifest.getTitle());
        newEntry.setDescription(manifest.getDescription());
        newEntry.setDescriptionAbstract(manifest.getDescriptionAbstract());
        newEntry.setRepositoryUrl(repositoryUrl);
        newEntry.setCopyrightHolders(manifest.getCopyrightHolders());
        newEntry.setImage(manifest.getImage());
        newEntry.setLicense(manifest.getLicense());
        newEntry.setAuthors(manifest.getAuthors());
        newEntry.setSearchTags(manifest.getSearchTags());

        newEntry.setDeleted(false);

        return newEntry;
    }
}
