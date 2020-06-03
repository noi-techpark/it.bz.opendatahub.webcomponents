package it.bz.opendatahub.webcomponents.deliveryservice.exception;

import org.springframework.http.HttpStatus;

public abstract class CoreException extends RuntimeException {
	private static final long serialVersionUID = -4817356189524214948L;

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
