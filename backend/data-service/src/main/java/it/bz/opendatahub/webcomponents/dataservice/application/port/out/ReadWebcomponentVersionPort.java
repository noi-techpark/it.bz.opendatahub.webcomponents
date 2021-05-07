package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

public interface ReadWebcomponentVersionPort {
	WebcomponentVersion getLatestVersionOfWebcomponent(@NonNull String webcomponentId);

	Map<String, WebcomponentVersion> getLatestVersionOfEachWebcomponent(@NonNull List<String> webcomponentIds);

	WebcomponentVersion getSpecificVersionOfWebcomponent(@NonNull String webcomponentUuid, @NonNull String versionTag);

	List<WebcomponentVersion> listAllVersionsOfWebcomponent(@NonNull String webcomponentUuid);

	List<WebcomponentVersion> getAllWithScheduledLighthouseUpdate();
}
