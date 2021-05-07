package it.bz.opendatahub.webcomponents.dataservice.exception.impl;

public class MetricsInvalidRequestException extends Exception {
	public MetricsInvalidRequestException() {
	}

	public MetricsInvalidRequestException(String message) {
		super(message);
	}

	public MetricsInvalidRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public MetricsInvalidRequestException(Throwable cause) {
		super(cause);
	}

	public MetricsInvalidRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
