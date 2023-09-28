package com.addyai.addyaiservice.controller.details;

import com.addyai.addyaiservice.models.AccountDetails;
import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.ads.AdDetails;
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

    ResponseEntity<CampaignDetails> fetchCampaignDetailsByName(String customerId, String campaignName);

    ResponseEntity<List<AdDetails>> fetchAdDetailsByAdGroupResourceName(String customerId, String adGroupResourceName);
}
