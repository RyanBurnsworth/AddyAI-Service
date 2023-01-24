package com.addyai.addyaiservice.exception;

import com.addyai.addyaiservice.models.error.DatabaseError;
import com.addyai.addyaiservice.models.error.GamsError;
import com.addyai.addyaiservice.models.error.InternalError;
import com.addyai.addyaiservice.repos.DatabaseErrorRepository;
import com.addyai.addyaiservice.repos.GAMSErrorRepository;
import com.addyai.addyaiservice.utils.Logger;
import com.addyai.addyaiservice.utils.ProxyErrorBuilder;

public class ApiExceptionResolver {
    private GAMSErrorRepository gamsErrorRepository;

    private DatabaseErrorRepository databaseErrorRepository;

    public ApiExceptionResolver(GAMSErrorRepository gamsErrorRepository, DatabaseErrorRepository databaseErrorRepository) {
        this.gamsErrorRepository = gamsErrorRepository;
        this.databaseErrorRepository = databaseErrorRepository;
    }

    public void throwApiException(InternalError error, String exceptionMessage) {
        if (error instanceof GamsError) {
            throwGAMSException((GamsError) error, exceptionMessage);
        } else if (error instanceof DatabaseError) {
            throwDatabaseException((DatabaseError) error);
        }
    }

    private void throwGAMSException(GamsError gamsError, String exceptionMessage) {
        // add GAMS related error information to the GAMS error
        ProxyErrorBuilder.updateGAMSError(gamsError, exceptionMessage);

        try {
            // store the error in the database
            gamsErrorRepository.save(gamsError);
        } catch (Exception e) {
            System.out.println("Error saving the GAMS error");
        }

        // write to the daily logs
        Logger.writeErrorLog(gamsError);

        // throw exception
        throw new GAMSException(gamsError);
    }

    private void throwDatabaseException(DatabaseError databaseError) {
        try {
            // store the error in the database
            databaseErrorRepository.save(databaseError);
        } catch (Exception e) {
            System.out.println("Error saving error to database");
        }

        // write to the daily logs
        Logger.writeErrorLog(databaseError);
        throw new DatabaseException(databaseError);
    }
}
