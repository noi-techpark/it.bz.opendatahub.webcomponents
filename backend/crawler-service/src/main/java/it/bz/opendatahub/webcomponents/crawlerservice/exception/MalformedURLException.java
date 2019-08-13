package it.bz.opendatahub.webcomponents.crawlerservice.exception;

public class MalformedURLException extends CrawlerException {
    public MalformedURLException() {
    }

    public MalformedURLException(String message) {
        super(message);
    }

    public MalformedURLException(String message, Throwable cause) {
        super(message, cause);
    }

    public MalformedURLException(Throwable cause) {
        super(cause);
    }

    public MalformedURLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
