package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface ReplaceWebcomponentLogoUseCase {
	Webcomponent replaceLogo(String webcomponentUuid, WebcomponentLogoReplaceRequest request);

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	class WebcomponentLogoReplaceRequest {
		private String logoPngBase64;
	}
}
