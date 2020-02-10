package it.bz.opendatahub.webcomponents.crawlerservice.factory;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.Manifest;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.TagEntry;
import org.springframework.stereotype.Component;

@Component
public class WebcomponentVersionFactory {
    public WebcomponentVersionModel createFromTagEntry(String componentUuid, TagEntry tagEntry, Manifest manifest) {
        WebcomponentVersionModel newEntry = new WebcomponentVersionModel();
        newEntry.setWebcomponentUuid(componentUuid);
        newEntry.setVersionTag(tagEntry.getName());
        newEntry.setReleaseTimestamp(tagEntry.getRevisionDate());
        newEntry.setDist(manifest.getDist());
        newEntry.setConfiguration(manifest.getConfiguration());

        newEntry.setDeleted(false);

        return newEntry;
    }
}
