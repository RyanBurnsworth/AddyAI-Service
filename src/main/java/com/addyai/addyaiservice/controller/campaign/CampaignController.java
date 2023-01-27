package com.addyai.addyaiservice.controller.campaign;

import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.documents.MetricsDocument;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.List;

public interface CampaignController {
    ResponseEntity<List<CampaignDetails>> fetchAllCampaignDetails(String customerId);

    ResponseEntity<CampaignDetails> fetchCampaignByName(String customerId, String campaignName);

    ResponseEntity<List<MetricsDocument>> fetchCampaignMetricsByDateRange(String customerId, String campaignId, String startDate, String endDate) throws ParseException;
}
