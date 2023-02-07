package com.addyai.addyaiservice.services.cache;

public interface CachingService {
    void cacheMetrics(String customerId, String resourceId, String startDate, String endDate, int type);
    void cacheDetails(String customerId, String resourceId, String parentResourceId, int type);
}
