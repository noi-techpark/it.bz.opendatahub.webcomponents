package it.bz.opendatahub.webcomponents.deliveryservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by SirCotare on 08.03.2016.
 */

public abstract class CoreException extends RuntimeException {
    public CoreException(String message) {
        super(message);
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreException(Throwable cause) {
        super(cause);
    }

    public abstract HttpStatus getHttpStatus();

    public int getErrorCode() {
        return getHttpStatus().value();
    }
}
