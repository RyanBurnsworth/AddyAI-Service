package com.addyai.addyaiservice.exception;

public class GAMSException extends RuntimeException {
    public GAMSException() {
    }

    public GAMSException(String message) {
        super(message);
    }

    public GAMSException(String message, Throwable cause) {
        super(message, cause);
    }

    public GAMSException(Throwable cause) {
        super(cause);
    }

    public GAMSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
