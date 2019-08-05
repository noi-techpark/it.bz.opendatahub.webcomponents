package it.bz.opendatahub.webcomponents.dataservice.exception.impl;

import it.bz.opendatahub.webcomponents.dataservice.exception.CoreException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class NotFoundException extends CoreException {
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
