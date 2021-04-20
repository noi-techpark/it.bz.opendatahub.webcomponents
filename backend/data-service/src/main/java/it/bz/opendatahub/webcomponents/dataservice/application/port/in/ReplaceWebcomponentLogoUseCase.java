package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import lombok.Getter;
import lombok.Setter;

public interface ReplaceWebcomponentLogoUseCase {
	Webcomponent replaceLogo(String webcomponentUuid, WebcomponentLogoReplaceRequest request);

	@Getter
	@Setter
	class WebcomponentLogoReplaceRequest {
		private String logoPngBase64;
	}
}
