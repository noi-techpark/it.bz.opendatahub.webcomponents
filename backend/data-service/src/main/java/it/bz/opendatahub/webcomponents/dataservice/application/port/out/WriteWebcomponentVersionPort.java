package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import lombok.NonNull;

public interface WriteWebcomponentVersionPort {
	WebcomponentVersion saveWebcomponentVersion(@NonNull WebcomponentVersion webcomponentVersion);
}
