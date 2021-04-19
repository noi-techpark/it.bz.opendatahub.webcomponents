package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import lombok.Getter;
import lombok.Setter;

public interface CreateWebcomponentUseCase {
	Webcomponent createWebcomponent(WebcomponentCreateRequest request);

	@Getter
	@Setter
	class WebcomponentCreateRequest {

	}
}
