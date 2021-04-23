package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import lombok.NonNull;

import java.util.List;

public interface ReadWebcomponentVersionPort {
	WebcomponentVersion getLatestVersionOfWebcomponent(@NonNull String webcomponentId);

	WebcomponentVersion getSpecificVersionOfWebcomponent(@NonNull String webcomponentUuid, @NonNull String versionTag);

	List<WebcomponentVersion> listAllVersionsOfWebcomponent(@NonNull String webcomponentUuid);

	List<WebcomponentVersion> getAllWithScheduledLighthouseUpdate();
}
