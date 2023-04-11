package com.addyai.addyaiservice.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GenericErrorModel {
    Date timeStamp;

    int status;

    String errorCode;

    String errorMessage;
}
