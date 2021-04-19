package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;

public interface WriteWebcomponentVersionPort {
	WebcomponentVersion saveWebcomponentVersion(WebcomponentVersion webcomponentVersion);
}
