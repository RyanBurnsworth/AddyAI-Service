package com.addyai.addyaiservice.controller.resources;

import com.addyai.addyaiservice.models.AccountBasics;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResourceController {
    ResponseEntity<AccountBasics> getAccountBasics(String customerId);

    ResponseEntity<List<String>> validateAdResource(String customerId, AccountBasics.Ad ad);

    ResponseEntity<Void> createAd(String customerId, AccountBasics.Ad ad);
}
