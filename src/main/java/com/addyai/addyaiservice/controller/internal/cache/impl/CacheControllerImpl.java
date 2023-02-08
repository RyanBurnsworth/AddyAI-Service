package com.addyai.addyaiservice.controller.internal.cache.impl;

import com.addyai.addyaiservice.controller.internal.cache.CacheController;
import com.addyai.addyaiservice.models.requests.CampaignMetricsRequest;
import com.addyai.addyaiservice.services.cache.CachingService;
import com.addyai.addyaiservice.utils.Constants;
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
    @PostMapping("/account/details")
    public ResponseEntity<Void> cacheAccountDetails(@PathVariable String customerId) {
        cachingService.cacheDetails(customerId, "", "", 0);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping("/campaign/details")
    public ResponseEntity<Void> cacheCampaignDetails(@PathVariable String customerId) {
        cachingService.cacheDetails(customerId, "", "", 1);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping("/adgroup/details")
    public ResponseEntity<Void> cacheAdGroupDetails(@PathVariable String customerId, @RequestParam String campaignId) {
        cachingService.cacheDetails(customerId, "", campaignId, 2);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping("/ad/details")
    public ResponseEntity<Void> cacheAdDetails(@PathVariable String customerId, @RequestParam String adGroupId) {
        cachingService.cacheDetails(customerId, "", adGroupId, Constants.TYPE_AD);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping("/keyword/details")
    public ResponseEntity<Void> cacheKeywordDetails(@PathVariable String customerId, @RequestParam String adGroupId) {
        cachingService.cacheDetails(customerId, "", adGroupId, Constants.TYPE_KEYWORD);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping("/assets/details")
    public ResponseEntity<Void> cacheAssetDetails(@PathVariable String customerId) {
        cachingService.cacheDetails(customerId, "", "", Constants.TYPE_ASSET);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping("/conversion/details")
    public ResponseEntity<Void> cacheConversionDetails(@PathVariable String customerId) {
        cachingService.cacheDetails(customerId, "", "", Constants.TYPE_CONVERSION);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping("/campaign/metrics")
    public ResponseEntity<Void> cacheCampaignMetrics(@PathVariable String customerId,
                                                     @RequestBody CampaignMetricsRequest campaignMetricsRequest) {
        cachingService.cacheMetrics(customerId, campaignMetricsRequest.getCampaignId(),
                campaignMetricsRequest.getStartDate(), campaignMetricsRequest.getEndDate(), Constants.TYPE_ADGROUP);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
