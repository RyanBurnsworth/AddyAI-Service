package com.addyai.addyaiservice.models.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericError {
    private String timeStamp;

    private String errorCode;

    private String errorMessage;
}
