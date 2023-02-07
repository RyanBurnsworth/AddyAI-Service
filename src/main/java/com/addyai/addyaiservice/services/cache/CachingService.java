package com.addyai.addyaiservice.services.cache;

public interface CachingService {
    void fetchAndCacheAccountData(String customerId);

    void cacheMetrics(String customerId, String resourceId, String startDate, String endDate, int type);
    void cacheAccountDetails(String customerId);
    void cacheAllCampaignDetails(String customerId);
    void cacheAdgroupDetails(String customerId, String campaignId);
}
