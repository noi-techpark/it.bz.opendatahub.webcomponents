package it.bz.opendatahub.webcomponents.dataservice.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleLighthouseMetrics {
	private String resultDesktop;

	private String resultMobile;
}
