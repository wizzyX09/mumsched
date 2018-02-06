package edu.mum.mumsched.exception;

public class EntryException extends RuntimeException {
    public EntryException() {
        super();
    }

    public EntryException(String message) {
        super(message);
    }

    public EntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntryException(Throwable cause) {
        super(cause);
    }

    protected EntryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
