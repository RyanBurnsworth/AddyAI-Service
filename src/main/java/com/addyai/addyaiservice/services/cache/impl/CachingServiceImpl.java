package com.addyai.addyaiservice.services.cache.impl;

import com.addyai.addyaiservice.exception.ApiExceptionResolver;
import com.addyai.addyaiservice.models.*;
import com.addyai.addyaiservice.models.ads.AdDetails;
import com.addyai.addyaiservice.models.ads.ResponsiveSearchAdDetails;
import com.addyai.addyaiservice.models.assets.AssetDetails;
import com.addyai.addyaiservice.models.documents.*;
import com.addyai.addyaiservice.models.error.DatabaseError;
import com.addyai.addyaiservice.models.error.GamsError;
import com.addyai.addyaiservice.repos.*;
import com.addyai.addyaiservice.services.cache.CachingService;
import com.addyai.addyaiservice.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class CachingServiceImpl implements CachingService {
    // TODO Remove hardcoded urls and dummy postfixes
    private final static String GAMS_BASE_URL = "http://localhost:8080/api/v1/";
    private final static String CAMPAIGN_METRICS_POST_FIX = "/campaign/metrics/dummy";
    private final static String ADGROUP_METRICS_POST_FIX = "/adgroup/metrics/dummy";
    private final static String AD_METRICS_POST_FIX = "/ad/metrics/dummy";
    private final static String KEYWORD_METRICS_POST_FIX = "/keyword/metrics/dummy";

    private final static String ACCOUNT_DETAILS_POST_FIX = "/account/details";
    private final static String CAMPAIGN_DETAILS_POST_FIX = "/campaign/details";
    private final static String ADGROUP_DETAILS_POST_FIX = "/adgroup/details";
    private final static String AD_DETAILS_POST_FIX = "/ad/details";
    private final static String KEYWORD_DETAILS_POST_FIX = "/keyword/details";
    private final static String ASSET_DETAILS_POST_FIX = "/assets/details";
    private final static String CONVERSION_DETAILS_POST_FIX = "/conversions/details";
    private final static String METRICS_SAVE_FAILED = "METRICS_SAVE_FAILED";
    private final static String METRICS_REMOVE_FAILED = "METRICS_REMOVE_FAILED";
    private final static String DETAILS_SAVE_FAILED = "DETAILS_SAVE_FAILED";
    private final static String DETAILS_REMOVE_FAILED = "DETAILS_REMOVE_FAILED";

    private final static String START_DATE_POST_FIX = "&startDate=";
    private final static String END_DATE_POST_FIX = "&endDate=";
    private final static String CAMPAIGN_RES_PART = "/campaigns/";
    private final static String ADGROUP_RES_PART = "/adGroups";
    private final static String CUSTOMERS_RES_PART = "customers/";
    private final static String CAMPAIGN_ID_URL_PART = "?campaignId=";
    private final static String ADGROUP_ID_URL_PART = "?adGroupId=";

    private final AccountRepository accountRepository;
    private final CampaignRepository campaignRepository;
    private final AdGroupRepository adGroupRepository;
    private final AdRepository adRepository;
    private final KeywordRepository keywordRepository;
    private final AssetRepository assetRepository;
    private final ConversionRepository conversionRepository;

    private final MetricsRepository metricsRepository;

    private final ApiExceptionResolver resolver;

    public CachingServiceImpl(AccountRepository accountRepository,
                              CampaignRepository campaignRepository,
                              AdGroupRepository adGroupRepository,
                              AdRepository adRepository,
                              KeywordRepository keywordRepository,
                              AssetRepository assetRepository,
                              ConversionRepository conversionRepository,
                              MetricsRepository metricsRepository,
                              GAMSErrorRepository gamsErrorRepository,
                              DatabaseErrorRepository databaseErrorRepository) {
        this.accountRepository = accountRepository;
        this.campaignRepository = campaignRepository;
        this.adGroupRepository = adGroupRepository;
        this.adRepository = adRepository;
        this.keywordRepository = keywordRepository;
        this.assetRepository = assetRepository;
        this.conversionRepository = conversionRepository;
        this.metricsRepository = metricsRepository;
        resolver = new ApiExceptionResolver(gamsErrorRepository, databaseErrorRepository);
    }

    /**
     * Fetch and store the campaign metrics in the Campaign_Metrics collection
     *
     * @param customerId the customer id of the Google Ads account
     * @param resourceId the id of the campaign to fetch metrics from
     * @param startDate  the start of the date range
     * @param endDate    the end of the date range
     * @param type       the type of resource (0 - Account, 1 - Campaign, etc.)
     */
    @Override
    public void cacheMetrics(String customerId, String resourceId, String startDate, String endDate, int type) {
        List<MetricsDocument> metricsDocuments = new ArrayList<>();
        ResponseEntity<MetricsDocument[]> response;
        String url = GAMS_BASE_URL + customerId;
        String datePostFix = START_DATE_POST_FIX + startDate + END_DATE_POST_FIX + endDate;

        if (type == Constants.TYPE_CAMPAIGN) {
            url = url + CAMPAIGN_METRICS_POST_FIX + "?campaignResourceName=" + resourceId + datePostFix;
        } else if (type == Constants.TYPE_ADGROUP) {
            url = url + ADGROUP_METRICS_POST_FIX + ADGROUP_ID_URL_PART + resourceId + "&campaignId=" + resourceId + datePostFix;
        } else if (type == Constants.TYPE_AD) {
            url = url + AD_METRICS_POST_FIX + ADGROUP_ID_URL_PART + resourceId + "&adId=" + resourceId + datePostFix;
        } else if (type == Constants.TYPE_KEYWORD) {
            url = url + KEYWORD_METRICS_POST_FIX + ADGROUP_ID_URL_PART + resourceId + "&keywordId=" + resourceId + datePostFix;
        } else {
            System.out.println("Error");
            // todo Throw an exception
        }

        try {
            // fetch campaign metrics from GAMS for a given customer id and date range
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.getForEntity(url, MetricsDocument[].class);

            // extract the response body
            metricsDocuments = Arrays.asList(Objects.requireNonNull(response.getBody()));

            // add customerId to the records
            metricsDocuments.forEach(document -> document.setCustomerId(customerId));
        } catch (Exception ex) {
            GamsError gamsError = new GamsError();
            gamsError.setCustomerId(customerId);
            gamsError.setFailedUrl(url);
            gamsError.setCampaignId(resourceId);

            resolver.throwApiException(gamsError, ex.getMessage());
        }

        // remove existing documents to avoid duplication
        removeExistingMetricsDocuments(metricsDocuments, customerId, type, startDate, endDate);

        try {
            // store the campaign metrics in the collection
            metricsRepository.saveAll(metricsDocuments);
        } catch (Exception e) {
            // throw a database error
            DatabaseError databaseError = new DatabaseError();
            databaseError.setErrorMessage(e.getMessage());
            databaseError.setStatusCode(500);
            databaseError.setTimestamp(new Timestamp(new Date().getTime()).toString());
            databaseError.setFailedUrl(url);
            databaseError.setErrorCode(METRICS_SAVE_FAILED);

            resolver.throwApiException(databaseError, e.getMessage());
        }
    }

    public void cacheDetails(String customerId, String resourceId, String parentResourceId, int type) {
        List<BaseDetails> details = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String url = GAMS_BASE_URL;

        // fetch the documents from Google through GAMS
        try {
            if (type == Constants.TYPE_ACCOUNT) {
                url = url + customerId + ACCOUNT_DETAILS_POST_FIX;
                ResponseEntity<AccountDetails> response = restTemplate.getForEntity(url, AccountDetails.class);
                details.add(response.getBody());
            } else if (type == Constants.TYPE_CAMPAIGN) {
                url = url + customerId + CAMPAIGN_DETAILS_POST_FIX;
                ResponseEntity<CampaignDetails[]> response = restTemplate.getForEntity(url, CampaignDetails[].class);
                details = Arrays.asList(Objects.requireNonNull(response.getBody()));
            } else if (type == Constants.TYPE_ADGROUP) {
                String campaignResourceName = CUSTOMERS_RES_PART + customerId + CAMPAIGN_RES_PART + parentResourceId;
                url = url + customerId + ADGROUP_DETAILS_POST_FIX + CAMPAIGN_ID_URL_PART + campaignResourceName;
                ResponseEntity<AdGroupDetails[]> response = restTemplate.getForEntity(url, AdGroupDetails[].class);
                details = Arrays.asList(Objects.requireNonNull(response.getBody()));
            } else if (type == Constants.TYPE_AD) {
                url = url + customerId + AD_DETAILS_POST_FIX + ADGROUP_ID_URL_PART + parentResourceId;
                ResponseEntity<AdDetails[]> response = restTemplate.getForEntity(url, AdDetails[].class);
                details = Arrays.asList(Objects.requireNonNull(response.getBody()));
            } else if (type == Constants.TYPE_KEYWORD) {
                url = url + customerId + KEYWORD_DETAILS_POST_FIX + ADGROUP_ID_URL_PART + parentResourceId;
                ResponseEntity<KeywordDetails[]> response = restTemplate.getForEntity(url, KeywordDetails[].class);
                details = Arrays.asList(Objects.requireNonNull(response.getBody()));
            } else if (type == Constants.TYPE_ASSET) {
                url = url + customerId + ASSET_DETAILS_POST_FIX;
                ResponseEntity<AssetDetails[]> response = restTemplate.getForEntity(url, AssetDetails[].class);
                details = Arrays.asList(Objects.requireNonNull(response.getBody()));
            } else if (type == Constants.TYPE_CONVERSION) {
                url = url + customerId + CONVERSION_DETAILS_POST_FIX;
                ResponseEntity<ConversionDetails[]> response = restTemplate.getForEntity(url, ConversionDetails[].class);
                details = Arrays.asList(Objects.requireNonNull(response.getBody()));
             }
            else {
                throw new InvalidParameterException("An invalid type parameter has been passed.");
            }
        } catch (Exception ex) {
            GamsError gamsError = new GamsError();
            gamsError.setCustomerId(customerId);
            gamsError.setFailedUrl(url);

            resolver.throwApiException(gamsError, ex.getMessage());
        }

        // save the documents to the Mongo Database
        try {
            if (type == Constants.TYPE_ACCOUNT) {
                AccountDocument accountDocument = new AccountDocument();
                accountDocument.setAccountDetails((AccountDetails) details.get(0));
                accountDocument.setCustomerId(customerId);
                accountDocument.setLastUpdated(new Timestamp(System.currentTimeMillis()).toString());

                removeExistingDetailsDocuments(customerId, "", null, Constants.TYPE_ACCOUNT);
                accountRepository.save(accountDocument);
            } else if (type == Constants.TYPE_CAMPAIGN) {
                // extract the campaign documents into a list
                List<CampaignDocument> campaignDocumentList = new ArrayList<>();
                details.forEach((campaignDetails -> {
                    CampaignDocument campaignDocument = new CampaignDocument();
                    campaignDocument.setCampaignDetails((CampaignDetails) campaignDetails);
                    campaignDocument.setCustomerId(customerId);
                    campaignDocument.setLastUpdated(new Timestamp(System.currentTimeMillis()).toString());

                    campaignDocumentList.add(campaignDocument);
                }));

                // remove older versions of campaign documents from the DB
                removeExistingDetailsDocuments(customerId, "", details, Constants.TYPE_CAMPAIGN);

                campaignRepository.saveAll(campaignDocumentList);
            } else if (type == Constants.TYPE_ADGROUP) {
                List<AdGroupDocument> adGroupDocumentList = new ArrayList<>();
                details.forEach(adGroupDetails -> {
                    AdGroupDocument adGroupDocument = new AdGroupDocument();
                    adGroupDocument.setAdGroupDetails((AdGroupDetails) adGroupDetails);
                    adGroupDocument.setCustomerId(customerId);
                    adGroupDocument.setLastUpdated(new Timestamp(System.currentTimeMillis()).toString());

                    adGroupDocumentList.add(adGroupDocument);
                });

                removeExistingDetailsDocuments(customerId, parentResourceId, details, Constants.TYPE_ADGROUP);
                adGroupRepository.saveAll(adGroupDocumentList);
            } else if (type == Constants.TYPE_AD) {
                List<AdDocument> adDocumentList = new ArrayList<>();
                details.forEach(adDetails -> {
                    AdDocument adDocument = new AdDocument();
                    adDocument.setAdDetails((AdDetails) adDetails);
                    adDocument.setCustomerId(customerId);
                    adDocument.setLastUpdated(new Timestamp(System.currentTimeMillis()).toString());

                    adDocumentList.add(adDocument);
                });

                removeExistingDetailsDocuments(customerId, parentResourceId, details, Constants.TYPE_AD);
                adRepository.saveAll(adDocumentList);
            } else if (type == Constants.TYPE_KEYWORD) {
                List<KeywordDocument> keywordDocumentList = new ArrayList<>();
                details.forEach(keywordDetails -> {
                    KeywordDocument keywordDocument = new KeywordDocument();
                    keywordDocument.setKeywordDetails((KeywordDetails) keywordDetails);
                    keywordDocument.setCustomerId(customerId);
                    keywordDocument.setLastUpdated(new Timestamp(System.currentTimeMillis()).toString());

                    keywordDocumentList.add(keywordDocument);
                });

                removeExistingDetailsDocuments(customerId, parentResourceId, details, Constants.TYPE_KEYWORD);
                keywordRepository.saveAll(keywordDocumentList);
            } else if (type == Constants.TYPE_ASSET) {
                List<AssetDocument> assetDocumentList = new ArrayList<>();
                details.forEach(assetDetails -> {
                    AssetDocument assetDocument = new AssetDocument();
                    assetDocument.setAssetDetails((AssetDetails) assetDetails);
                    assetDocument.setCustomerId(customerId);
                    assetDocument.setLastUpdated(new Timestamp(System.currentTimeMillis()).toString());

                    assetDocumentList.add(assetDocument);
                });

                removeExistingDetailsDocuments(customerId, "", details, Constants.TYPE_ASSET);
                assetRepository.saveAll(assetDocumentList);
            } else if (type == Constants.TYPE_CONVERSION) {
                List<ConversionDocument> conversionDocumentsList = new ArrayList<>();
                details.forEach(conversionDetails -> {
                    ConversionDocument conversionDocument = new ConversionDocument();
                    conversionDocument.setConversionDetails((ConversionDetails) conversionDetails);
                    conversionDocument.setCustomerId(customerId);
                    conversionDocument.setLastUpdated(new Timestamp(System.currentTimeMillis()).toString());

                    conversionDocumentsList.add(conversionDocument);
                });

                removeExistingDetailsDocuments(customerId, "", details, Constants.TYPE_CONVERSION);
                conversionRepository.saveAll(conversionDocumentsList);
            }
        } catch (Exception e) {
            DatabaseError databaseError = new DatabaseError();
            databaseError.setErrorCode(DETAILS_SAVE_FAILED);
            databaseError.setFailedUrl(url);
            databaseError.setTimestamp(new Timestamp(new Date().getTime()).toString());
            databaseError.setErrorMessage(e.getMessage());
            databaseError.setStatusCode(500);

            resolver.throwApiException(databaseError, e.getMessage());
        }
    }

    private void removeExistingDetailsDocuments(String customerId, String parentResourceId, List<BaseDetails> detailsList, int type) {
        try {
            if (type == Constants.TYPE_ACCOUNT) {
                AccountDocument existingAccount = accountRepository.findAccountDocumentByCustomerId(customerId);
                if (existingAccount != null) {
                    accountRepository.delete(existingAccount);
                }
            } else if (type == Constants.TYPE_CAMPAIGN) {
                List<CampaignDocument> existingDocuments = campaignRepository.findAllCampaignDocumentsByCustomerId(customerId);
                List<CampaignDocument> removableDocuments = new ArrayList<>();

                detailsList.forEach(details -> {
                    CampaignDetails campaignDetails = (CampaignDetails) details;
                    existingDocuments.forEach(document -> {
                        if (campaignDetails.getCampaignResourceName().equals(document.getCampaignDetails().getCampaignResourceName())) {
                            removableDocuments.add(document);
                        }
                    });
                });

                if (!removableDocuments.isEmpty())
                    campaignRepository.deleteAll(removableDocuments);
            } else if (type == Constants.TYPE_ADGROUP) {
                String campaignResourceName = CUSTOMERS_RES_PART + customerId + CAMPAIGN_RES_PART + parentResourceId;
                List<AdGroupDocument> removableDocuments = new ArrayList<>();
                List<AdGroupDocument> existingDocuments = adGroupRepository.findAllAdGroupDocumentsByCampaign(customerId,
                        campaignResourceName);

                detailsList.forEach(details -> {
                    AdGroupDetails adGroupDetails = (AdGroupDetails) details;
                    existingDocuments.forEach(document -> {
                        if (adGroupDetails.getAdGroupResourceName().equals(document.getAdGroupDetails().getAdGroupResourceName())) {
                            removableDocuments.add(document);
                        }
                    });
                });

                if (!removableDocuments.isEmpty())
                    adGroupRepository.deleteAll(removableDocuments);
            } else if (type == Constants.TYPE_AD) {
                String adGroupResourceName = CUSTOMERS_RES_PART + customerId + ADGROUP_RES_PART + parentResourceId;
                List<AdDocument> removableDocuments = new ArrayList<>();
                List<AdDocument> existingDocuments = adRepository.findAllAdGroupDocumentsByAdGroup(customerId,
                        adGroupResourceName);

                detailsList.forEach(details -> {
                    AdDetails adDetails = (ResponsiveSearchAdDetails) details;
                    existingDocuments.forEach(document -> {
                        if (adDetails.getAdId() == document.getAdDetails().getAdId()) {
                            removableDocuments.add(document);
                        }
                    });
                });

                if (!removableDocuments.isEmpty())
                    adRepository.deleteAll(removableDocuments);
            } else if (type == Constants.TYPE_KEYWORD) {
                String adGroupResourceName = CUSTOMERS_RES_PART + customerId + ADGROUP_RES_PART + parentResourceId;
                List<KeywordDocument> removableDocuments = new ArrayList<>();
                List<KeywordDocument> existingDocuments = keywordRepository.findAllKeywordDocumentsByAdGroup(customerId,
                        adGroupResourceName);

                detailsList.forEach(details -> {
                    KeywordDetails keywordDetails = (KeywordDetails) details;
                    existingDocuments.forEach(document -> {
                        if (keywordDetails.getKeywordId() == document.getKeywordDetails().getKeywordId()) {
                            removableDocuments.add(document);
                        }
                    });
                });

                if (!removableDocuments.isEmpty())
                    keywordRepository.deleteAll(removableDocuments);
            } else if (type == Constants.TYPE_ASSET) {
                List<AssetDocument> removableDocuments = new ArrayList<>();
                List<AssetDocument> existingDocuments = assetRepository.findAllAssetDocuments(customerId);

                detailsList.forEach(details -> {
                    AssetDetails assetDetails = (AssetDetails) details;
                    existingDocuments.forEach(document -> {
                        if (assetDetails.getAssetId() == document.getAssetDetails().getAssetId()) {
                            removableDocuments.add(document);
                        }
                    });
                });

                if (!removableDocuments.isEmpty())
                    assetRepository.deleteAll(removableDocuments);
            } else if (type == Constants.TYPE_CONVERSION) {
                List<ConversionDocument> removableDocuments = new ArrayList<>();
                List<ConversionDocument> existingDocuments = conversionRepository.findAllConversionDocuments(customerId);

                detailsList.forEach(details -> {
                    ConversionDetails conversionDetails = (ConversionDetails) details;
                    existingDocuments.forEach(document -> {
                        if (conversionDetails.getActionId() == document.getConversionDetails().getActionId()) {
                            removableDocuments.add(document);
                        }
                    });
                });

                if (!removableDocuments.isEmpty())
                    conversionRepository.deleteAll(removableDocuments);
            }
        } catch (Exception e) {
            DatabaseError databaseError = new DatabaseError();
            databaseError.setStatusCode(500);
            databaseError.setErrorMessage(e.getMessage());
            databaseError.setFailedUrl("");
            databaseError.setErrorCode(DETAILS_REMOVE_FAILED);

            resolver.throwApiException(databaseError, e.getMessage());
        }
    }

    /**
     * Remove documents that exist in both the database and the incoming metrics documents to avoid duplication.
     *
     * @param incomingMetricDocuments the incoming metrics documents to be added to the database collection
     * @param customerId              the id of the customer account
     * @param type                    the type of resource
     * @param startDate               the date at the beginning of the date range
     * @param endDate                 the date at the end of the date range
     */
    private void removeExistingMetricsDocuments(List<MetricsDocument> incomingMetricDocuments,
                                                String customerId, int type, String startDate, String endDate) {
        // fetch all the existing documents by customerId, type and date range
        List<MetricsDocument> metricsDocumentsList = metricsRepository
                .findAllMetricsByCustomerIdAndType(customerId, type);

        List<MetricsDocument> metricsDocumentsToBeRemoved = new ArrayList<>();

        metricsDocumentsList.forEach(metricsDocument -> {
            incomingMetricDocuments.forEach(incomingMetricDocument -> {
                // metrics documents are distinct by date
                if (metricsDocument.getDate().equals(incomingMetricDocument.getDate())) {
                    metricsDocumentsToBeRemoved.add(metricsDocument);
                }
            });
        });

        try {
            if (!metricsDocumentsToBeRemoved.isEmpty())
                metricsRepository.deleteAll(metricsDocumentsToBeRemoved);
        } catch (Exception e) {
            DatabaseError databaseError = new DatabaseError();
            databaseError.setErrorCode(METRICS_REMOVE_FAILED);
            databaseError.setFailedUrl("");
            databaseError.setTimestamp(new Timestamp(new Date().getTime()).toString());
            databaseError.setErrorMessage(e.getMessage());
            databaseError.setStatusCode(500);

            resolver.throwApiException(databaseError, e.getMessage());
        }
    }
}
