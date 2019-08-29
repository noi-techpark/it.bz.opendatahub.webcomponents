package it.bz.opendatahub.webcomponents.crawlerservice.service;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.model.OriginModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.TagEntry;

public interface WebcomponentVersionService {
    void purgeVersionForTag(OriginModel origin, TagEntry tagEntry);

    void createVersionFromTag(OriginModel origin, TagEntry tagEntry);

    void cascadeDeletedTags();

    WebcomponentVersionModel getLatestVersionForWebcomponent(String uuid);
}
