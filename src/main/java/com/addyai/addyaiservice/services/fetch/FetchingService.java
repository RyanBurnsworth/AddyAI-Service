package com.addyai.addyaiservice.services.fetch;

import com.addyai.addyaiservice.models.*;
import com.addyai.addyaiservice.models.ads.AdDetails;
import com.addyai.addyaiservice.models.assets.AssetDetails;
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

    List<CampaignDetails> getAllCampaignDetails(String customerId);

    CampaignDetails getCampaignDetailsByName(String customerId, String campaignName);

    CampaignDetails getCampaignDetailsByResourceName(String customerId, String campaignResourceName);

    List<AdGroupDetails> getAllAdGroupDetails(String customerId, String campaignResourceName);

    AdGroupDetails getAdGroupDetails(String customerId, String adGroupResourceName);

    List<AdDetails> getAdDetails(String customerId, String adGroupResourceName);

    List<KeywordDetails> getKeywordDetailsByAdGroup(String customerId, String adGroupResourceName);

    List<AssetDetails> getAssetDetails(String customerId);
}
