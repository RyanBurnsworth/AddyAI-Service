package com.addyai.addyaiservice.controller;

import com.addyai.addyaiservice.models.CampaignDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CampaignController {
    ResponseEntity<List<CampaignDetails>> fetchAllCampaignDetails(String customerId);

    ResponseEntity<Void> updateCache(String customerId);
    ResponseEntity<CampaignDetails> fetchCampaignByName(String customerId, String campaignName);
}
