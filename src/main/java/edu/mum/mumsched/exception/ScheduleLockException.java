package edu.mum.mumsched.exception;

public class ScheduleLockException extends RuntimeException {
    public ScheduleLockException() {
        super("Schedule can't be deleted,some students are currently enrolled!!");
    }

    public ScheduleLockException(String message) {
        super(message);
    }

    public ScheduleLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScheduleLockException(Throwable cause) {
        super(cause);
    }

    protected ScheduleLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
