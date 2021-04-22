package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;

public interface UpdateWebcomponentVersionMetricsUseCase {
	WebcomponentVersion updateMetricsForWebcomponentVersion(WebcomponentVersion webcomponentVersion);
}
