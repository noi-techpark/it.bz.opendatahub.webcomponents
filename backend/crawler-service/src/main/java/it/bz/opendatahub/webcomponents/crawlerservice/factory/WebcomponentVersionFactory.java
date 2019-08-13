package it.bz.opendatahub.webcomponents.crawlerservice.factory;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.TagEntry;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WebcomponentVersionFactory {
    public WebcomponentVersionModel createFromTagEntry(String componentUuid, TagEntry tagEntry) {
        WebcomponentVersionModel newEntry = new WebcomponentVersionModel();
        newEntry.setWebcomponentUuid(componentUuid);
        newEntry.setVersionTag(tagEntry.getName());
        newEntry.setReleaseTimestamp(new Date());

        return newEntry;
    }
}
