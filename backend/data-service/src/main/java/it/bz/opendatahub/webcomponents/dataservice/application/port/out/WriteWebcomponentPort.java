package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;

public interface WriteWebcomponentPort {
	Webcomponent saveWebcomponent(Webcomponent webcomponent);
}
