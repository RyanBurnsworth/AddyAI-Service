package com.addyai.addyaiservice.services.cache;

public interface CachingService {
    void fetchAndCacheAccountData(String customerId);

    void cacheMetrics(String customerId, String resourceId, String startDate, String endDate, int type);
    void cacheAllCampaignDetails(String customerId);
}
