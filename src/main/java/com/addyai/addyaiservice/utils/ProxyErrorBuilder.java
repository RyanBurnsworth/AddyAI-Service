package com.addyai.addyaiservice.utils;

import com.addyai.addyaiservice.models.error.GamsError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProxyErrorBuilder {
    private static final String TIMESTAMP = "timeStamp";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";

    /**
     * Convert a GAMS API Error into a status code and {@link com.addyai.addyaiservice.models.error.GenericError}
     *
     * @param gamsError   a GamsError object to be updated
     * @param errorString the error response from GAMS
     * @return an updated {@link GamsError}
     */
    public static GamsError updateGAMSError(GamsError gamsError, String errorString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // extract the JSON string from the error string
            String errorObject = extractErrorObject(errorString);

            // extract the status code from the error string
            String statusCode = extractStatusCode(errorString);

            // update the status code
            gamsError.setStatusCode(Integer.parseInt(statusCode));

            // convert the JSON string into a valuable JSON node
            JsonNode node = mapper.readTree(errorObject);
            node.fields().forEachRemaining(field -> {
                if (field.getKey().equals(TIMESTAMP))
                    gamsError.setTimestamp(field.getValue().asText());

                if (field.getKey().equals(ERROR_MESSAGE))
                    gamsError.setErrorMessage(field.getValue().asText());

                if (field.getKey().equals(ERROR_CODE))
                    gamsError.setErrorCode(field.getValue().asText());
            });

        } catch (Exception e) {
            System.out.println("Error extracting JSON string from errorString. Error: " + e);
        }

        return gamsError;
    }

    private static String extractStatusCode(String errorMessage) {
        return errorMessage.substring(0, 3);
    }

    private static String extractErrorObject(String errorMessage) {
        String obj = errorMessage.substring(7, (errorMessage.length() - 1));
        return obj.replace("'", "");
    }
}
