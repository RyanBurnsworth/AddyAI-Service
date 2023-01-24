package com.addyai.addyaiservice.exception.resolver;

import com.addyai.addyaiservice.exception.DatabaseException;
import com.addyai.addyaiservice.exception.GAMSException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApiExceptionResolver {
    private static final String INTERNAL_DATABASE_ERROR_MSG = "We are experiencing a database issue. Please try again later";
    private static final String INTERNAL_GAMS_ERROR_MSG = "We are experiencing a GAMS issue. Please try again later";
    private static final String INTERNAL_SERVER_ERROR_MSG = "We are experiencing a server issue. Please try again later";

    public static ResponseStatusException handleApiException(Exception ex) {
        if (ex instanceof DatabaseException) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_DATABASE_ERROR_MSG, ex);
        } else if (ex instanceof GAMSException) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_GAMS_ERROR_MSG, ex);
        } else {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG + ex.getMessage(), ex);
        }
    }
}
