package com.addyai.addyaiservice.exception;

import com.addyai.addyaiservice.models.error.InternalError;

public class DatabaseException extends RuntimeException {

    public InternalError internalError;

    public DatabaseException() {
    }

    public DatabaseException(InternalError internalError) {
        this.internalError = internalError;
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
