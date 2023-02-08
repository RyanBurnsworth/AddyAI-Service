package com.addyai.addyaiservice.controller.internal.cache.impl;

import com.addyai.addyaiservice.controller.internal.cache.CacheController;
import com.addyai.addyaiservice.services.cache.CachingService;
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
    @PostMapping("/account")
    public ResponseEntity<Void> cacheCustomerAccount(@PathVariable String customerId,
                                                     @RequestParam String startDate,
                                                     @RequestParam String endDate) {
        cachingService.cacheClientAccount(customerId, startDate, endDate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
