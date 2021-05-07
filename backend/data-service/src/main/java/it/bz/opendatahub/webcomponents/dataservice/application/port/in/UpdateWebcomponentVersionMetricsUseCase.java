package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import lombok.NonNull;

public interface UpdateWebcomponentVersionMetricsUseCase {
	WebcomponentVersion updateMetricsForWebcomponentVersion(@NonNull WebcomponentVersion webcomponentVersion);
}
