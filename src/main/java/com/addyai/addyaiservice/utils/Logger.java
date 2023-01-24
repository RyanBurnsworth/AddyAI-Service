package com.addyai.addyaiservice.utils;

import com.addyai.addyaiservice.models.error.InternalError;

import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.Date;

public class Logger {
    private static final String BASE_DIRECTORY = System.getProperty("user.home");

    public static void writeErrorLog(InternalError internalError) {
        String today = new Timestamp(new Date().getTime()).toString().substring(0, 10)
                .replace("-", "")
                .replace(":", "")
                .replace(" ", "")
                + ".txt";

        String filename = BASE_DIRECTORY + "\\" + today;

        try {
            System.out.println("Writing filename: " + filename);
            FileWriter logWriter = new FileWriter(filename, true);
            logWriter.write("Timestamp: " + internalError.getTimestamp() + " ");
            logWriter.write("Error Code: " + internalError.getErrorCode() + " ");
            logWriter.write("Error Message: " + internalError.getErrorMessage() + " ");
            logWriter.write("Status Code: " + internalError.getStatusCode() + " ");
            logWriter.write("Failed URL: " + internalError.getFailedUrl() + " ");
            logWriter.close();
        } catch (Exception ex) {
            System.out.println("Error writing log file");
        }
    }
}
