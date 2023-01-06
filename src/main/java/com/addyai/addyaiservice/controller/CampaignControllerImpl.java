package com.addyai.addyaiservice.controller;

import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.services.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{customerId}/campaign/")
public class CampaignControllerImpl implements CampaignController {

    private final CampaignService campaignService;

    public CampaignControllerImpl(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @Override
    @PostMapping("update/cache/all")
    public ResponseEntity<Void> updateCache(@PathVariable String customerId) {
        campaignService.fetchAndUpdateCache(customerId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @GetMapping("fetch/all")
    public ResponseEntity<List<CampaignDetails>> fetchAllCampaignDetails(@PathVariable String customerId) {
        List<CampaignDetails> campaignDetailsList = campaignService.fetchAllCampaignDetails(customerId);

        return new ResponseEntity<>(campaignDetailsList, HttpStatus.OK);
    }

    @Override
    @GetMapping("fetch")
    public ResponseEntity<CampaignDetails> fetchCampaignByName(@PathVariable String customerId,
                                                               @RequestParam String campaignName) {
        CampaignDetails campaignDetails = campaignService.fetchCampaignDetailsByName(customerId, campaignName);
        return new ResponseEntity<>(campaignDetails, HttpStatus.OK);
    }
}
