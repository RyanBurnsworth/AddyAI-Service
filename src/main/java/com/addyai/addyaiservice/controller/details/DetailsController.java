package com.addyai.addyaiservice.controller.details;

import com.addyai.addyaiservice.models.AccountDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * A controller interface for fetching details from database
 *
 * @author Ryan.Burnsworth
 */
public interface DetailsController {
    ResponseEntity<List<AccountDetails>> fetchAllAccountDetails(String customerId);

    ResponseEntity<AccountDetails> fetchSingleAccountDetails(String customerId);
}
