package com.addyai.addyaiservice.services.fetch.impl;

import com.addyai.addyaiservice.models.documents.*;
import com.addyai.addyaiservice.repos.*;
import com.addyai.addyaiservice.services.fetch.FetchingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.addyai.addyaiservice.utils.Constants.*;

/**
 * Fetches documents from the MongoDB
 *
 */
@AllArgsConstructor
@Service
public class FetchingServiceImpl implements FetchingService {
    private final AccountRepository accountRepository;
    private final CampaignRepository campaignRepository;
    private final AdGroupRepository adGroupRepository;
    private final AdRepository adRepository;
    private final KeywordRepository keywordRepository;
    private final ConversionRepository conversionRepository;
    private final AssetRepository assetRepository;
    private final MetricsRepository metricsRepository;

    @Override
    public List<MetricsDocument> fetchMetricsByCustomerIdAndType(String customerId, String startDate, String endDate, int type) {
        Date start;
        Date end;

        // parse the dates into Date formats
        try {
            start = new SimpleDateFormat(DATE_FORMAT).parse(startDate);
            end = new SimpleDateFormat(DATE_FORMAT).parse(endDate);
        } catch (Exception e) {
            System.out.println("Error parsing start and end date before fetching overall metrics");
            return null;
        }

        return metricsRepository.findMetricsByCustomerIdAndTypeWithinDateRange(customerId, type, start, end);
    }

    @Override
    public List<MetricsDocument> fetchMetricsByCustomerIdAndResourceId(String customerId, String resourceId, String startDate, String endDate) {
        Date start;
        Date end;

        // parse the dates into Date formats
        try {
            start = new SimpleDateFormat(DATE_FORMAT).parse(startDate);
            end = new SimpleDateFormat(DATE_FORMAT).parse(endDate);
        } catch (Exception e) {
            System.out.println("Error parsing start and end date before fetching overall metrics");
            return null;
        }

        return metricsRepository.findMetricsByCustomerIdAndResourceIdWithinDateRange(customerId, resourceId, start, end);
    }

    @Override
    public List<String> getCampaignResourceIds(String customerId) {
        List<String> campaignIds = new ArrayList<>();
        List<CampaignDocument> campaignDocumentList = new ArrayList<>(campaignRepository
                .findAllCampaignDocumentsByCustomerId(customerId));

        campaignDocumentList.forEach(campaignDocument ->
                campaignIds.add(String.valueOf(campaignDocument.getCampaignDetails().getCampaignId())));

        return campaignIds;
    }

    @Override
    public List<String> getAdGroupResourceIds(String customerId, String campaignId) {
        List<String> adGroupIds = new ArrayList<>();
        String campaignResourceName = CUSTOMERS_RES_PART + customerId + CAMPAIGN_RES_PART + campaignId;
        List<AdGroupDocument> adGroupDocumentList = new ArrayList<>(adGroupRepository
                .findAllAdGroupDocumentsByCampaign(customerId, campaignResourceName));

        adGroupDocumentList.forEach(adGroupDocument ->
                adGroupIds.add(String.valueOf(adGroupDocument.getAdGroupDetails().getAdGroupId())));

        return adGroupIds;
    }

    @Override
    public List<String> getAdResourceIds(String customerId, String adGroupId) {
        List<String> adIds = new ArrayList<>();
        String adGroupResourceName = CUSTOMERS_RES_PART + customerId + ADGROUP_RES_PART + adGroupId;
        List<AdDocument> adDocumentList = new ArrayList<>(adRepository
                .findAllAdGroupDocumentsByAdGroup(customerId, adGroupResourceName));

        adDocumentList.forEach(adDocument ->
                adIds.add(adDocument.getId()));

        return adIds;
    }

    @Override
    public List<String> getKeywordResourceIds(String customerId, String adGroupId) {
        List<String> keywordIds = new ArrayList<>();
        String adGroupResourceName = CUSTOMERS_RES_PART + customerId + ADGROUP_RES_PART + adGroupId;
        List<KeywordDocument> keywordDocumentList = new ArrayList<>(keywordRepository
                .findAllKeywordDocumentsByAdGroup(customerId, adGroupResourceName));

        keywordDocumentList.forEach(adDocument -> keywordIds.add(adDocument.getId()));

        return keywordIds;
    }
}
