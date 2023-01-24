package com.addyai.addyaiservice.models.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalError {
    String timestamp;

    String failedUrl;

    String errorMessage;

    String errorCode;

    int statusCode;
}
