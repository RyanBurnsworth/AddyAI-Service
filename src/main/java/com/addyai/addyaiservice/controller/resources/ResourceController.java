package com.addyai.addyaiservice.controller.resources;

import com.addyai.addyaiservice.models.AccountBasics;
import org.springframework.http.ResponseEntity;

public interface ResourceController {
    ResponseEntity<AccountBasics> getAccountBasics(String customerId);

    ResponseEntity<Void> createAd(String customerId, AccountBasics.Ad ad);
}
