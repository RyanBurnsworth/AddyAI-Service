package com.addyai.addyaiservice.services.proxy.impl;

import com.addyai.addyaiservice.models.AccountBasics;
import com.addyai.addyaiservice.models.ads.ResponsiveSearchAdDetails;
import com.addyai.addyaiservice.repos.proxy.ProxyRepository;
import com.addyai.addyaiservice.services.proxy.ProxyService;
import com.addyai.addyaiservice.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ProxyServiceImpl implements ProxyService {
    private final ProxyRepository proxyRepository;

    public ProxyServiceImpl(ProxyRepository proxyRepository) {
        this.proxyRepository = proxyRepository;
    }

    @Override
    public ResponseEntity<Void> createAd(String customerId, AccountBasics.Ad ad) {
        String cleanResourceId = Utils.cleanAdGroupResourceId(ad.getParentResourceName());

        List<String> headlines = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<String> paths = new ArrayList<>();

        headlines.add(ad.getHeadline1());
        headlines.add(ad.getHeadline2());
        headlines.add(ad.getHeadline3());

        descriptions.add(ad.getDescription1());
        descriptions.add(ad.getDescription2());

        paths.add(ad.getPath1());
        paths.add(ad.getPath2());

        ResponsiveSearchAdDetails adDetails = new ResponsiveSearchAdDetails();
        adDetails.setType("responsive");
        adDetails.setAdType("responsive");
        adDetails.setAdGroupResourceName(ad.getParentResourceName());
        adDetails.setHeadlines(headlines);
        adDetails.setAdStatus(2);
        adDetails.setPaths(paths);
        adDetails.setDescriptions(descriptions);
        adDetails.setFinalUrl(ad.getFinalUrl());

        List<ResponsiveSearchAdDetails> adDetailsList = new ArrayList<>();
        adDetailsList.add(adDetails);

        return proxyRepository.createAd(customerId, cleanResourceId, adDetailsList);
    }

    @Override
    public List<String> validateAd(String customerId, AccountBasics.Ad ad) {
        String cleanResourceId = Utils.cleanAdGroupResourceId(ad.getParentResourceName());

        List<String> headlines = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<String> paths = new ArrayList<>();

        headlines.add(ad.getHeadline1());
        headlines.add(ad.getHeadline2());
        headlines.add(ad.getHeadline3());

        descriptions.add(ad.getDescription1());
        descriptions.add(ad.getDescription2());

        paths.add(ad.getPath1());
        paths.add(ad.getPath2());

        ResponsiveSearchAdDetails adDetails = new ResponsiveSearchAdDetails();
        adDetails.setType("responsive");
        adDetails.setAdType("responsive");
        adDetails.setParentResourceName(ad.getParentResourceName());
        adDetails.setAdGroupResourceName(ad.getResourceName());
        adDetails.setHeadlines(headlines);
        adDetails.setAdStatus(2);
        adDetails.setPaths(paths);
        adDetails.setDescriptions(descriptions);
        adDetails.setFinalUrl(ad.getFinalUrl());

        ResponseEntity<String[]> errors = proxyRepository.validateAd(customerId, cleanResourceId, adDetails);
        return Arrays.asList(Objects.requireNonNull(errors.getBody()));
    }
}
