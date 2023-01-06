package com.addyai.addyaiservice.services;

import com.addyai.addyaiservice.models.CampaignDetails;

import java.util.List;

public interface CampaignService {
    List<CampaignDetails> fetchAllCampaignDetails(String customerId);

    CampaignDetails fetchCampaignDetailsByName(String customerId, String campaignName);

    void fetchAndUpdateCache(String customerId);
}
