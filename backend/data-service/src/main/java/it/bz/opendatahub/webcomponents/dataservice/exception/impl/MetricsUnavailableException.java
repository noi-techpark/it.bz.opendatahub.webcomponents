// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.exception.impl;

public class MetricsUnavailableException extends Exception {
	public MetricsUnavailableException() {
	}

	public MetricsUnavailableException(String message) {
		super(message);
	}

	public MetricsUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public MetricsUnavailableException(Throwable cause) {
		super(cause);
	}

	public MetricsUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
