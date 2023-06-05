// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.exception.impl;

public class MetricsErrorException extends Exception {
	public MetricsErrorException() {
	}

	public MetricsErrorException(String message) {
		super(message);
	}

	public MetricsErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public MetricsErrorException(Throwable cause) {
		super(cause);
	}

	public MetricsErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
