package com.addyai.addyaiservice.services.campaign;

import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.documents.metrics.CampaignMetricsDocument;

import java.text.ParseException;
import java.util.List;

public interface CampaignService {
    List<CampaignDetails> fetchAllCampaignDetails(String customerId);

    CampaignDetails fetchCampaignDetailsByName(String customerId, String campaignName);

    List<CampaignMetricsDocument> fetchMetricsByDateRange(String campaignResourceName, String startDate, String endDate) throws ParseException;
}
