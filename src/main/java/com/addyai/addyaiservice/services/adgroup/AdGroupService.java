package com.addyai.addyaiservice.services.adgroup;

import com.addyai.addyaiservice.models.AdGroupDetails;

import java.util.List;

public interface AdGroupService {
    List<AdGroupDetails> fetchAllAdGroupDetails(String customerId, String campaignResourceName);

    AdGroupDetails fetchAdGroupDetails(String customerId, String adGroupResourceName);

    void fetchAndUpdateCache(String customerId);
}
