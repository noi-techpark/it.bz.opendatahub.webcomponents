package it.bz.opendatahub.webcomponents.deliveryservice.exception.impl;

import it.bz.opendatahub.webcomponents.deliveryservice.exception.CoreException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class NotFoundException extends CoreException {
	private static final long serialVersionUID = 5678846975150474920L;

	@Getter
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND; // don't make static: breaks inherited abstract getter

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
