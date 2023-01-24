package com.addyai.addyaiservice.exception;

import com.addyai.addyaiservice.models.error.InternalError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GenericExceptionHandler {
    @ExceptionHandler(value = {GAMSException.class})
    protected ResponseEntity<InternalError> handleGAMSException(GAMSException ex, WebRequest request) {
        if (ex.internalError.getStatusCode() == 400) {
            return new ResponseEntity<>(ex.internalError, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ex.internalError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {DatabaseException.class})
    protected ResponseEntity<InternalError> handleDatabaseException(DatabaseException ex, WebRequest request) {

        return new ResponseEntity<>(ex.internalError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
