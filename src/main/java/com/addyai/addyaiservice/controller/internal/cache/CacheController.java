package com.addyai.addyaiservice.controller.internal.cache;

import com.addyai.addyaiservice.models.requests.CampaignMetricsRequest;
import org.springframework.http.ResponseEntity;

public interface CacheController {
    ResponseEntity<Void> cacheCampaignMetrics(String customerId, CampaignMetricsRequest campaignMetricsRequest);

    ResponseEntity<Void> cacheCampaignDetails(String customerId);

    ResponseEntity<Void> cacheAccountDetails(String customerId);

    ResponseEntity<Void> cacheAdGroupDetails(String customerId, String campaignId);
    ResponseEntity<Void> cacheAdDetails(String customerId, String adGroupId);

    ResponseEntity<Void> cacheKeywordDetails(String customerId, String adGroupId);
}
