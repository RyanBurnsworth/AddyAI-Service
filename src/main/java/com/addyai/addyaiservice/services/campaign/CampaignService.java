package com.addyai.addyaiservice.services.campaign;

import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.documents.MetricsDocument;

import java.text.ParseException;
import java.util.List;

public interface CampaignService {
    List<CampaignDetails> fetchAllCampaignDetails(String customerId);

    CampaignDetails fetchCampaignDetailsByName(String customerId, String campaignName);
}
