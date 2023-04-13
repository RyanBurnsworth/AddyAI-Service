package com.addyai.addyaiservice.models.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(value = "GAMS_Error")
public class GamsError extends InternalError {

    String customerId;

    String resourceId;
    String parentResourceId;

    @Override
    public String getTimestamp() {
        return super.getTimestamp();
    }

    @Override
    public void setTimestamp(String timestamp) {
        super.setTimestamp(timestamp);
    }

    @Override
    public String getFailedUrl() {
        return super.getFailedUrl();
    }

    @Override
    public void setFailedUrl(String failedUrl) {
        super.setFailedUrl(failedUrl);
    }

    @Override
    public String getErrorMessage() {
        return super.getErrorMessage();
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        super.setErrorMessage(errorMessage);
    }

    @Override
    public String getErrorCode() {
        return super.getErrorCode();
    }

    @Override
    public void setErrorCode(String errorCode) {
        super.setErrorCode(errorCode);
    }

    @Override
    public int getStatusCode() {
        return super.getStatusCode();
    }

    @Override
    public void setStatusCode(int statusCode) {
        super.setStatusCode(statusCode);
    }
}
