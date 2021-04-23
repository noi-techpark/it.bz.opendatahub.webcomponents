package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentConfiguration;
import lombok.NonNull;

public interface GetWebcomponentConfigurationUseCase {
	WebcomponentConfiguration getConfiguration(@NonNull String uuid);

	WebcomponentConfiguration getConfiguration(@NonNull String uuid, @NonNull String versionTag);
}
