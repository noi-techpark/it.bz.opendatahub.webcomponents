package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.lighthouse;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.GoogleLighthouseMetrics;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.GoogleLighthousePort;

public class GoogleLighthouseAdapter implements GoogleLighthousePort {
	@Override
	public GoogleLighthouseMetrics getMetricsForUrl(String url) {
		return null;
	}
}
