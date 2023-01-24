package com.addyai.addyaiservice.exception;

import com.addyai.addyaiservice.models.error.InternalError;

public class GAMSException extends RuntimeException {
    public InternalError internalError = new InternalError();

    public GAMSException() {
    }

    public GAMSException(InternalError internalError) {
        this.internalError = internalError;
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
