package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.GoogleLighthouseMetrics;

public interface GoogleLighthousePort {
	GoogleLighthouseMetrics getMetricsForUrl(String url);
}
