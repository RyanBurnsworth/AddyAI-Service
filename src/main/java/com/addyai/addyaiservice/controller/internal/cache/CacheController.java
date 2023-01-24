package com.addyai.addyaiservice.controller.internal.cache;

import com.addyai.addyaiservice.models.requests.CampaignMetricsRequest;
import org.springframework.http.ResponseEntity;

public interface CacheController {
    ResponseEntity<Void> cacheCampaignMetrics(String customerId, CampaignMetricsRequest campaignMetricsRequest);

    ResponseEntity<Void> cacheAllCampaignDetails(String customerId);
}
