package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import lombok.NonNull;

public interface GetWebcomponentVersionUseCase {
	WebcomponentVersion getLatestVersionOfWebcomponent(@NonNull String webcomponentId);

	WebcomponentVersion getSpecificVersionOfWebcomponent(@NonNull String webcomponentUuid, @NonNull String versionTag);
}
