package com.addyai.addyaiservice.controller.internal.cache;

import com.addyai.addyaiservice.models.requests.CampaignMetricsRequest;
import org.springframework.http.ResponseEntity;

public interface CacheController {
    ResponseEntity<Void> cacheCustomerAccount(String customerId, String startDate, String endDate);
}
