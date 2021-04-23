package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import lombok.NonNull;

public interface GetWebcomponentUseCase {
	Webcomponent getWebcomponentById(@NonNull String uuid);
}
