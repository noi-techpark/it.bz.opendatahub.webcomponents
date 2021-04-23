package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.GoogleLighthouseMetrics;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.MetricsErrorException;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.MetricsInvalidRequestException;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.MetricsUnavailableException;
import lombok.NonNull;

public interface GoogleLighthousePort {
	GoogleLighthouseMetrics getMetricsForUrl(@NonNull String url) throws MetricsErrorException, MetricsUnavailableException, MetricsInvalidRequestException;
}
