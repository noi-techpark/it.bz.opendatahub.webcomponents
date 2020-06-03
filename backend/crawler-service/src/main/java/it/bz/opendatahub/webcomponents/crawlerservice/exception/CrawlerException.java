package it.bz.opendatahub.webcomponents.crawlerservice.exception;

public class CrawlerException extends RuntimeException {
	private static final long serialVersionUID = 4250745722325483682L;

	public CrawlerException() {
    }

    public CrawlerException(String message) {
        super(message);
    }

    public CrawlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrawlerException(Throwable cause) {
        super(cause);
    }

    public CrawlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
