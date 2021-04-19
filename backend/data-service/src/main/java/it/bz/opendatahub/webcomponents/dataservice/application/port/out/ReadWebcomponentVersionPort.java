package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;

import java.util.List;

public interface ReadWebcomponentVersionPort {
	WebcomponentVersion getLatestVersionOfWebcomponent(String webcomponentId);

	WebcomponentVersion getSpecificVersionOfWebcomponent(String webcomponentUuid, String versionTag);

	List<WebcomponentVersion> listAllVersionsOfWebcomponent(String webcomponentUuid);
}
