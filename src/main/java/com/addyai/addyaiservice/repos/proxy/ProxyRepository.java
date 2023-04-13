package com.addyai.addyaiservice.repos.proxy;

import com.addyai.addyaiservice.models.ads.ResponsiveSearchAdDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProxyRepository {
    ResponseEntity<Void> createAd(String customerId, String adGroupId, List<ResponsiveSearchAdDetails> adDetails);

    ResponseEntity<String[]> validateAd(String customerId, String adGroupId, ResponsiveSearchAdDetails adDetails);
}
