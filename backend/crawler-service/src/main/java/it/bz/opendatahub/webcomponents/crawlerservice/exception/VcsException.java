package it.bz.opendatahub.webcomponents.crawlerservice.exception;

public class VcsException extends CrawlerException {
    public VcsException() {
    }

    public VcsException(String message) {
        super(message);
    }

    public VcsException(String message, Throwable cause) {
        super(message, cause);
    }

    public VcsException(Throwable cause) {
        super(cause);
    }

    public VcsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
