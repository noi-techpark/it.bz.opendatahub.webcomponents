package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;

public interface GetWebcomponentUseCase {
	Webcomponent getWebcomponentById(String uuid);
}
