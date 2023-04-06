package com.addyai.addyaiservice.controller.cache;

import org.springframework.http.ResponseEntity;

public interface CacheController {
    ResponseEntity<Void> cacheCustomerAccount(String customerId, String startDate, String endDate);
}
