package com.addyai.addyaiservice.services.fetch.impl;

import com.addyai.addyaiservice.models.AccountBasics;
import com.addyai.addyaiservice.models.AccountDetails;
import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.ads.AdDetails;
import com.addyai.addyaiservice.models.ads.ResponsiveSearchAdDetails;
import com.addyai.addyaiservice.models.assets.CalloutExtensionDetails;
import com.addyai.addyaiservice.models.assets.SitelinkDetails;
import com.addyai.addyaiservice.models.documents.*;
import com.addyai.addyaiservice.repos.*;
import com.addyai.addyaiservice.services.fetch.FetchingService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.addyai.addyaiservice.utils.Constants.*;

/**
 * Fetches documents from the MongoDB
 */
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

    public FetchingServiceImpl(AccountRepository accountRepository, CampaignRepository campaignRepository, AdGroupRepository adGroupRepository, AdRepository adRepository, KeywordRepository keywordRepository, ConversionRepository conversionRepository, AssetRepository assetRepository, MetricsRepository metricsRepository) {
        this.accountRepository = accountRepository;
        this.campaignRepository = campaignRepository;
        this.adGroupRepository = adGroupRepository;
        this.adRepository = adRepository;
        this.keywordRepository = keywordRepository;
        this.conversionRepository = conversionRepository;
        this.assetRepository = assetRepository;
        this.metricsRepository = metricsRepository;
    }

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

    @Override
    public AccountDetails getAccountDetails(String customerId) {
        AccountDocument accountDocument = accountRepository.findAccountDocumentByCustomerId(customerId);
        return accountDocument.getAccountDetails();
    }

    @Override
    public CampaignDetails getCampaignDetails(String customerId, String campaignName) {
        CampaignDocument campaignDocument = campaignRepository.findCampaignDocumentByName(customerId, campaignName);
        return campaignDocument.getCampaignDetails();
    }

    @Override
    public List<AdDetails> getAdDetails(String customerId, String adGroupResourceName) {
        List<AdDetails> adDetailsList = new ArrayList<>();
        List<AdDocument> adDocumentList = adRepository.findAllAdGroupDocumentsByAdGroup(customerId, adGroupResourceName);
        System.out.println("AGRN: " + adGroupResourceName);
        System.out.println("OUTPUT: " + adDocumentList.toString());
        adDocumentList.forEach((adDocument -> {
            adDetailsList.add(adDocument.getAdDetails());
        }));

        return adDetailsList;
    }

    /**
     * Fetch the basics of account resources rather than a full set of resources
     *
     * @param customerId the id of the customer Google Ads account
     * @return {@link AccountBasics}
     */
    public AccountBasics getAccountBasics(String customerId) {
        AccountBasics accountBasics = new AccountBasics();

        // Fetch the active campaign documents on the account
        List<AccountBasics.ParentResource> campaignResourceList = new ArrayList<>();
        List<CampaignDocument> campaigns = campaignRepository.findAllCampaignDocumentsByCustomerId(customerId);
        campaigns.forEach(campaignDocument -> {
            AccountBasics.ParentResource campaignResource = new AccountBasics.ParentResource();
            campaignResource.setResourceName(campaignDocument.getCampaignDetails().getCampaignResourceName());
            campaignResource.setName(campaignDocument.getCampaignDetails().getCampaignName());
            campaignResourceList.add(campaignResource);
        });
        accountBasics.setCampaigns(campaignResourceList);

        // Fetch the active adGroup documents assigned to the active campaigns
        List<AccountBasics.ChildResource> adGroupResourceList = new ArrayList<>();
        campaignResourceList.forEach(campaignResource -> {
            // extract the campaign resource name and pass to adGroupRepository
            List<AdGroupDocument> adGroups = adGroupRepository.findAllAdGroupDocumentsByCampaign(customerId, campaignResource.getResourceName());
            adGroups.forEach(adGroupDocument -> {
                AccountBasics.ChildResource adGroupResource = new AccountBasics.ChildResource();
                adGroupResource.setResourceName(adGroupDocument.getAdGroupDetails().getAdGroupResourceName());
                adGroupResource.setParentResourceName(campaignResource.getResourceName());
                adGroupResource.setName(adGroupDocument.getAdGroupDetails().getAdGroupName());
                adGroupResourceList.add(adGroupResource);
            });
            accountBasics.setAdGroups(adGroupResourceList);

            // Fetch the active ads assigned to the active ad groups
            List<AccountBasics.Ad> adResourceList = new ArrayList<>();
            List<AccountBasics.FinalUrl> finalUrlResourceList = new ArrayList<>();
            adGroupResourceList.forEach(adGroupResource -> {
                List<AdDocument> adDocuments = adRepository.findAllAdGroupDocumentsByAdGroup(customerId, adGroupResource.getResourceName());
                adDocuments.forEach(adDocument -> {
                    AccountBasics.Ad adResource = new AccountBasics.Ad();
                    ResponsiveSearchAdDetails adDetails = (ResponsiveSearchAdDetails) adDocument.getAdDetails();
                    if (adDetails.getHeadlines() != null && adDetails.getHeadlines().size() > 0)
                        adResource.setHeadline1(adDetails.getHeadlines().get(0));

                    if (adDetails.getHeadlines() != null && adDetails.getHeadlines().size() > 1)
                        adResource.setHeadline2(adDetails.getHeadlines().get(1));

                    if (adDetails.getHeadlines() != null && adDetails.getHeadlines().size() > 2)
                        adResource.setHeadline3(adDetails.getHeadlines().get(2));

                    if (adDetails.getDescriptions() != null && adDetails.getDescriptions().size() > 0)
                        adResource.setDescription1(adDetails.getDescriptions().get(0));
                    if (adDetails.getDescriptions() != null && adDetails.getDescriptions().size() > 1)
                        adResource.setDescription2(adDetails.getDescriptions().get(1));

                    if (adDetails.getPaths() != null && adDetails.getPaths().size() > 0)
                        adResource.setPath1(adDetails.getPaths().get(0));
                    if (adDetails.getPaths() != null && adDetails.getPaths().size() > 1)
                        adResource.setPath2(adDetails.getPaths().get(1));

                    if (adDetails.getFinalUrl() != null)
                        adResource.setFinalUrl(adDetails.getFinalUrl());

                    adResource.setClicks(0);
                    adResource.setConversions(0);
                    adResource.setCtr("");
                    adResource.setCpc("");
                    adResource.setParentResourceName(adGroupResource.getResourceName());
                    adResource.setResourceName(adDocument.getId());

                    adResourceList.add(adResource);

                    // create the final url
                    AccountBasics.FinalUrl finalUrl = new AccountBasics.FinalUrl();
                    finalUrl.setFinalUrl(adDetails.getFinalUrl());
                    finalUrl.setParentResourceName(adGroupResource.getResourceName());

                    finalUrlResourceList.add(finalUrl);
                });
                accountBasics.setTopAdsByAdGroup(adResourceList);
                accountBasics.setFinalUrls(finalUrlResourceList);

                List<AccountBasics.Keyword> keywordsResourceList = new ArrayList<>();
                adGroupResourceList.forEach(agResource -> {
                    List<KeywordDocument> keywordDocuments = keywordRepository.findAllKeywordDocumentsByAdGroup(customerId, agResource.getResourceName());
                    keywordDocuments.forEach(keywordDocument -> {
                        AccountBasics.Keyword keywordResource = new AccountBasics.Keyword();
                        keywordResource.setResourceName(keywordDocument.getId());
                        keywordResource.setText(keywordDocument.getKeywordDetails().getKeywordText());
                        keywordResource.setParentResourceName(agResource.getResourceName());

                        keywordsResourceList.add(keywordResource);
                    });
                });
                accountBasics.setTop10KeywordsByAdGroup(keywordsResourceList);

                // Fetch the active extensions assigned to the account
                List<AccountBasics.Extension> extensionsResourceList = new ArrayList<>();
                List<AssetDocument> assetDocuments = assetRepository.findAllAssetDocuments(customerId);

                assetDocuments.forEach(asset -> {
                    AccountBasics.Extension extension = new AccountBasics.Extension();
                    // Sitelinks AssetType is 11
                    if (asset.getAssetDetails().getAssetType() == 11) {
                        SitelinkDetails sitelinkDetails = (SitelinkDetails) asset.getAssetDetails();
                        extension.setLink(sitelinkDetails.getFinalUrlList().get(0));
                        extension.setResourceName(sitelinkDetails.getAssetName());
                        extension.setText(sitelinkDetails.getLinkText());
                        extension.setType("sitelinks");

                        extensionsResourceList.add(extension);
                    } else if (asset.getAssetDetails().getAssetType() == 9) {
                        CalloutExtensionDetails calloutExtensionDetails = (CalloutExtensionDetails) asset.getAssetDetails();
                        extension.setText(calloutExtensionDetails.getText());
                        extension.setType("callout");

                        extensionsResourceList.add(extension);
                    }
                });
                accountBasics.setExtensions(extensionsResourceList);
            });
        });
        return accountBasics;
    }
}
