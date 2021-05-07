package it.bz.opendatahub.webcomponents.common.logging;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.entries;

public class CustomRequestLoggingFilter extends AbstractRequestLoggingFilter {

	Logger logger = LoggerFactory.getLogger(CustomRequestLoggingFilter.class);

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter((ServletRequest) request, response);
		} finally {
			if (!this.isAsyncStarted(request)) {
				logger.info("Request finished", entries(logData(request, response)));
			}
		}
	}

	private Map<String, Object> logData(HttpServletRequest request, HttpServletResponse response) {
		final HashMap<String, Object> result = new HashMap<>();
		result.put("uri", request.getRequestURI());
		result.put("queryString", request.getQueryString());
		result.put("user", request.getRemoteUser());
		result.put("userAgent", request.getHeader("User-Agent"));
		result.put("status", response.getStatus());

		return result;
	}

	@Override
	protected void beforeRequest(@NonNull HttpServletRequest httpServletRequest, @NonNull String s) {
	}

	@Override
	protected void afterRequest(@NonNull HttpServletRequest httpServletRequest, @NonNull String s) {
	}
}
