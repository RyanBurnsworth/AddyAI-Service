package com.addyai.addyaiservice.controller.details;

import com.addyai.addyaiservice.models.AccountDetails;
import com.addyai.addyaiservice.models.AdGroupDetails;
import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.KeywordDetails;
import com.addyai.addyaiservice.models.ads.AdDetails;
import com.addyai.addyaiservice.services.fetch.FetchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/{customerId}")
public class DetailsControllerImpl implements DetailsController {
    private final FetchingService fetchingService;

    public DetailsControllerImpl(FetchingService fetchingService) {
        this.fetchingService = fetchingService;
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDetails>> fetchAllAccountDetails(@PathVariable String customerId) {
        List<AccountDetails> accountDetailsList = new ArrayList<>();

        AccountDetails accountDetails = this.fetchingService.getAccountDetails(customerId);
        accountDetailsList.add(accountDetails);
        return ResponseEntity.ok(accountDetailsList);
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/")
    public ResponseEntity<AccountDetails> fetchSingleAccountDetails(@PathVariable String customerId) {
        AccountDetails accountDetails = this.fetchingService.getAccountDetails(customerId);
        return ResponseEntity.ok(accountDetails);
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/campaigns")
    public ResponseEntity<List<CampaignDetails>> fetchAllCampaignDetails(@PathVariable String customerId) {
        List<CampaignDetails> campaignDetailsList =  this.fetchingService.getAllCampaignDetails(customerId);
        return ResponseEntity.ok(campaignDetailsList);
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/campaign")
    public ResponseEntity<CampaignDetails> fetchCampaignDetailsByName(@PathVariable String customerId, @RequestParam String campaignName) {
        CampaignDetails campaignDetails = this.fetchingService.getCampaignDetails(customerId, campaignName);
        return ResponseEntity.ok(campaignDetails);
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/adGroups")
    public ResponseEntity<List<AdGroupDetails>> fetchAllAdGroupDetails(@PathVariable String customerId, @RequestParam String campaignResourceName) {
        String resourceName = "customers/" + customerId + "/campaigns/" + campaignResourceName;
        List<AdGroupDetails> adGroupDetailsList = this.fetchingService.getAllAdGroupDetails(customerId, resourceName);
        return ResponseEntity.ok(adGroupDetailsList);
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/adGroup")
    public ResponseEntity<AdGroupDetails> fetchAdGroupDetails(@PathVariable String customerId, @RequestParam String adGroupResourceName) {
        String resourceName = "customers/" + customerId + "/adGroups/" + adGroupResourceName;
        AdGroupDetails adGroupDetails = this.fetchingService.getAdGroupDetails(customerId, resourceName);
        return ResponseEntity.ok(adGroupDetails);
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/ads")
    public ResponseEntity<List<AdDetails>> fetchAdDetailsByAdGroupResourceName(@PathVariable String customerId, @RequestParam String adGroupResourceName) {
        String resourceName = "customers/" + customerId + "/adGroups/" + adGroupResourceName;
        List<AdDetails> adDetailsList = this.fetchingService.getAdDetails(customerId, resourceName);
        return ResponseEntity.ok(adDetailsList);
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/keywords")
    public ResponseEntity<List<KeywordDetails>> fetchKeywordDetailsByAdGroup(@PathVariable String customerId, @RequestParam String adGroupResourceName) {
        String resourceName = "customers/" + customerId + "/adGroups/" + adGroupResourceName;
        List<KeywordDetails> keywordDetailsList = this.fetchingService.getKeywordDetailsByAdGroup(customerId, resourceName);
        return ResponseEntity.ok(keywordDetailsList);
    }
}
