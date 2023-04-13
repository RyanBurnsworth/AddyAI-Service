package com.addyai.addyaiservice.repos.proxy;

import com.addyai.addyaiservice.models.ads.ResponsiveSearchAdDetails;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProxyRepositoryImpl implements ProxyRepository {
    @Override
    public ResponseEntity<Void> createAd(String customerId, String adGroupId, List<ResponsiveSearchAdDetails> adDetails) {
        String url = "http://localhost:8080/api/v1/" + customerId + "/ad/create?adGroupId=" + adGroupId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<ResponsiveSearchAdDetails>> httpEntity = new HttpEntity<>(adDetails, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, Void.class);
    }

    @Override
    public ResponseEntity<String[]> validateAd(String customerId, String adGroupId, ResponsiveSearchAdDetails adDetails) {
        String url = "http://localhost:8080/api/v1/" + customerId + "/ad/validate?adGroupId=" + adGroupId;
        List<ResponsiveSearchAdDetails> adDetailsList = new ArrayList<>();
        adDetailsList.add(adDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<ResponsiveSearchAdDetails>> httpEntity = new HttpEntity<>(adDetailsList, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, String[].class);
    }
}
