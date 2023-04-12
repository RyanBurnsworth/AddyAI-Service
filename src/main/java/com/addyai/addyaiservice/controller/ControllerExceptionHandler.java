package com.addyai.addyaiservice.controller;

import com.addyai.addyaiservice.exception.GenericErrorModel;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = {HttpClientErrorException.Forbidden.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String deniedPermissionException(HttpClientErrorException.Forbidden ex) {
        return "Denied permission";
    }

    @ExceptionHandler(value = {HttpClientErrorException.BadRequest.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String constraintViolationException(HttpClientErrorException.BadRequest ex) {
        Gson gson = new Gson();
        GenericErrorModel errorModel = gson.fromJson(ex.getResponseBodyAsString(), GenericErrorModel.class);
        return errorModel.getErrorMessage();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerError(Exception ex) {
        return "Internal error";
    }
}
