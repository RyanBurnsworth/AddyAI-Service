package com.addyai.addyaiservice.controller.campaign;

import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.documents.metrics.CampaignMetricsDocument;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.List;

public interface CampaignController {
    ResponseEntity<List<CampaignDetails>> fetchAllCampaignDetails(String customerId);

    ResponseEntity<CampaignDetails> fetchCampaignByName(String customerId, String campaignName);

    ResponseEntity<List<CampaignMetricsDocument>> fetchCampaignMetricsByDateRange(String customerId, String campaignId, String startDate, String endDate) throws ParseException;
}
