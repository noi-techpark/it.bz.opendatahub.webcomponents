package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import lombok.Getter;
import lombok.Setter;

public interface CreateWebcomponentVersionUseCase {
	WebcomponentVersion createWebcomponentVersion(WebcomponentVersionCreateRequest request);

	@Getter
	@Setter
	class WebcomponentVersionCreateRequest {

	}
}
