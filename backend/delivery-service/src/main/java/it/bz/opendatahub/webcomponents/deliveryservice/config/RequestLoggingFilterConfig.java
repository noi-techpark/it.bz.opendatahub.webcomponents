// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.deliveryservice.config;

import it.bz.opendatahub.webcomponents.common.logging.CustomRequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestLoggingFilterConfig {

	@Bean
	public CustomRequestLoggingFilter logFilter() {
		CustomRequestLoggingFilter filter
			= new CustomRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(false);
		filter.setIncludeHeaders(true);
		filter.setIncludeClientInfo(true);
		return filter;
	}

}
