package com.addyai.addyaiservice.services.adgroup.impl;

import com.addyai.addyaiservice.models.AdGroupDetails;
import com.addyai.addyaiservice.services.adgroup.AdGroupService;

import java.util.List;

public class AdGroupServiceImpl implements AdGroupService {
    @Override
    public List<AdGroupDetails> fetchAllAdGroupDetails(String customerId, String campaignResourceName) {
        return null;
    }

    @Override
    public AdGroupDetails fetchAdGroupDetails(String customerId, String adGroupResourceName) {
        return null;
    }

    @Override
    public void fetchAndUpdateCache(String customerId) {

    }
}
