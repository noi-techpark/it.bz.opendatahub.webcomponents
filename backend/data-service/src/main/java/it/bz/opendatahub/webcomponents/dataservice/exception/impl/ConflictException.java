// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.exception.impl;

import it.bz.opendatahub.webcomponents.dataservice.exception.CoreException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ConflictException extends CoreException {
	private static final long serialVersionUID = 7890037185267729717L;

	@Getter
    private final HttpStatus httpStatus = HttpStatus.CONFLICT; // don't make static: breaks inherited abstract getter

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(Throwable cause) {
        super(cause);
    }
}
