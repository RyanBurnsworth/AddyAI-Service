package com.addyai.addyaiservice.services.fetch;

import com.addyai.addyaiservice.models.AccountBasics;
import com.addyai.addyaiservice.models.AccountDetails;
import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.ads.AdDetails;
import com.addyai.addyaiservice.models.documents.MetricsDocument;

import java.util.List;

public interface FetchingService {
    List<MetricsDocument> fetchMetricsByCustomerIdAndType(String customerId, String startDate, String endDate, int type);

    List<MetricsDocument> fetchMetricsByCustomerIdAndResourceId(String customerId, String resourceId, String startDate, String endDate);

    List<String> getCampaignResourceIds(String customerId);

    List<String> getAdGroupResourceIds(String customerId, String campaignId);

    List<String> getAdResourceIds(String customerId, String adGroupId);

    List<String> getKeywordResourceIds(String customerId, String adGroupId);

    AccountBasics getAccountBasics(String customerId);

    AccountDetails getAccountDetails(String customerId);

    CampaignDetails getCampaignDetails(String customerId, String campaignName);

    List<AdDetails> getAdDetails(String customerId, String adGroupResourceName);
}
