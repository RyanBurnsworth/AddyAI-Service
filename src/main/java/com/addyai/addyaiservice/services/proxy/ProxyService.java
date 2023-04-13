package com.addyai.addyaiservice.services.proxy;

import com.addyai.addyaiservice.models.AccountBasics;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProxyService {
    ResponseEntity<Void> createAd(String customerId, AccountBasics.Ad ad);

    List<String> validateAd(String customerId, AccountBasics.Ad ad);
}
