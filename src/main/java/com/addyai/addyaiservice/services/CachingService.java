package com.addyai.addyaiservice.services;

public interface CachingService {
    void fetchAndCacheAccountData(String customerId);

    void cacheCampaignMetrics(String customerId, String campaignId, String startDate, String endDate);
    void cacheAllCampaignDetails(String customerId);
}
