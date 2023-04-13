package com.addyai.addyaiservice.models.error;

import java.util.List;

public class AdValidationError {
    List<String> errorMessage;

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }
}
