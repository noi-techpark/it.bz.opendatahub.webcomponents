package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import lombok.NonNull;
import org.springframework.web.bind.annotation.PathVariable;

public interface ScheduleWebcomponentVersionMetricsUpdateUseCase {
	WebcomponentVersion scheduleMetricsUpdate(@NonNull String webcomponentUuid, @NonNull String versionTag);
}
