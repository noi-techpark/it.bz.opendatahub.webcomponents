package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentConfiguration;

public interface GetWebcomponentConfigurationUseCase {
	WebcomponentConfiguration getConfiguration(String uuid);

	WebcomponentConfiguration getConfiguration(String uuid, String versionTag);
}
