package com.addyai.addyaiservice.controller.campaign.impl;

import com.addyai.addyaiservice.controller.campaign.CampaignController;
import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.documents.metrics.CampaignMetricsDocument;
import com.addyai.addyaiservice.services.campaign.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/{customerId}/campaign/")
public class CampaignControllerImpl implements CampaignController {

    private final CampaignService campaignService;

    public CampaignControllerImpl(CampaignService campaignService) {
        this.campaignService = campaignService;
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

    @Override
    @GetMapping("metrics")
    public ResponseEntity<List<CampaignMetricsDocument>> fetchCampaignMetricsByDateRange(@PathVariable String customerId,
                                                                                         @RequestParam String campaignResourceName,
                                                                                         @RequestParam String startDate,
                                                                                         @RequestParam String endDate) throws ParseException {
        return new ResponseEntity<>(campaignService.fetchMetricsByDateRange(customerId, campaignResourceName, startDate, endDate), HttpStatus.OK);
    }
}
