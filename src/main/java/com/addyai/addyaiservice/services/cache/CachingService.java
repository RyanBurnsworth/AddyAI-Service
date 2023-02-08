package com.addyai.addyaiservice.services.cache;

public interface CachingService {
    void cacheClientAccount(String customerId, String startDate, String endDate);
}
