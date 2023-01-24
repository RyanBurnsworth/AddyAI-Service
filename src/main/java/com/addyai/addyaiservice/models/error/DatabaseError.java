package com.addyai.addyaiservice.models.error;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Database_Error")
public class DatabaseError extends InternalError {
    private String collectionName;

    @Override
    public String getTimestamp() {
        return super.getTimestamp();
    }

    @Override
    public String getFailedUrl() {
        return super.getFailedUrl();
    }

    @Override
    public String getErrorMessage() {
        return super.getErrorMessage();
    }

    @Override
    public int getStatusCode() {
        return super.getStatusCode();
    }

    @Override
    public void setTimestamp(String timestamp) {
        super.setTimestamp(timestamp);
    }

    @Override
    public void setFailedUrl(String failedUrl) {
        super.setFailedUrl(failedUrl);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        super.setErrorMessage(errorMessage);
    }

    @Override
    public void setStatusCode(int statusCode) {
        super.setStatusCode(statusCode);
    }
}
