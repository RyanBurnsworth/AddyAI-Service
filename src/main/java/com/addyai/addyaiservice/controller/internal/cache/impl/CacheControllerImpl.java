package com.addyai.addyaiservice.controller.internal.cache.impl;

import com.addyai.addyaiservice.controller.internal.cache.CacheController;
import com.addyai.addyaiservice.exception.resolver.ApiExceptionResolver;
import com.addyai.addyaiservice.models.requests.CampaignMetricsRequest;
import com.addyai.addyaiservice.services.CachingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{customerId}/cache")
public class CacheControllerImpl implements CacheController {
    private final CachingService cachingService;

    public CacheControllerImpl(CachingService cachingService) {
        this.cachingService = cachingService;
    }

    @Override
    @PostMapping("/campaign/metrics")
    public ResponseEntity<Void> cacheCampaignMetrics(@PathVariable String customerId,
                                                     @RequestBody CampaignMetricsRequest campaignMetricsRequest) {
        try {
            cachingService.cacheCampaignMetrics(customerId, campaignMetricsRequest.getCampaignId(),
                    campaignMetricsRequest.getStartDate(), campaignMetricsRequest.getEndDate());
        } catch (Exception e) {
            throw ApiExceptionResolver.handleApiException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping("/campaign/details")
    public ResponseEntity<Void> cacheAllCampaignDetails(@PathVariable String customerId) {
        try {
            cachingService.cacheAllCampaignDetails(customerId);
        } catch (Exception e) {
            throw ApiExceptionResolver.handleApiException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
