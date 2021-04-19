package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;

public interface GetWebcomponentVersionUseCase {
	WebcomponentVersion getLatestVersionOfWebcomponent(String webcomponentId);

	WebcomponentVersion getSpecificVersionOfWebcomponent(String webcomponentUuid, String versionTag);
}
