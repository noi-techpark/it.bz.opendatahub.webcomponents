package it.bz.opendatahub.webcomponents.crawlerservice.factory;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.crawlerservice.data.mapping.Manifest;
import it.bz.opendatahub.webcomponents.crawlerservice.data.struct.TagEntry;
import it.bz.opendatahub.webcomponents.crawlerservice.repository.VcsApiRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class WebcomponentVersionFactory {
	private final VcsApiRepository vcsApiRepository;

	public WebcomponentVersionFactory(@Qualifier("githubApiRepository") VcsApiRepository vcsApiRepository) {
		this.vcsApiRepository = vcsApiRepository;
	}

	public WebcomponentVersionModel createFromTagEntry(String componentUuid, TagEntry tagEntry, Manifest manifest, String readMe, String license) {
        WebcomponentVersionModel newEntry = new WebcomponentVersionModel();
        newEntry.setWebcomponentUuid(componentUuid);
        newEntry.setVersionTag(tagEntry.getName());
        newEntry.setReleaseTimestamp(vcsApiRepository.getMetadataForCommit(tagEntry.getRemote(), tagEntry.getRevisionHash()).getDate());
        newEntry.setDist(manifest.getDist());
        newEntry.setConfiguration(manifest.getConfiguration());
        newEntry.setReadMe(readMe);
        newEntry.setLicenseAgreement(license);

        newEntry.setDeleted(false);

        return newEntry;
    }
}
