package com.addyai.addyaiservice.services.cache.impl;

import com.addyai.addyaiservice.exception.ApiExceptionResolver;
import com.addyai.addyaiservice.models.AccountDetails;
import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.documents.AccountDocument;
import com.addyai.addyaiservice.models.documents.CampaignDocument;
import com.addyai.addyaiservice.models.documents.MetricsDocument;
import com.addyai.addyaiservice.models.error.DatabaseError;
import com.addyai.addyaiservice.models.error.GamsError;
import com.addyai.addyaiservice.repos.*;
import com.addyai.addyaiservice.services.cache.CachingService;
import com.addyai.addyaiservice.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;

@Service
public class CachingServiceImpl implements CachingService {
    private final static String GAMS_BASE_URL = "http://localhost:8080/api/v1/";
    private final static String CAMPAIGN_METRICS_POST_FIX = "/campaign/metrics/dummy";
    private final static String ADGROUP_METRICS_POST_FIX = "/adgroup/metrics/dummy";
    private final static String AD_METRICS_POST_FIX = "/ad/metrics/dummy";
    private final static String KEYWORD_METRICS_POST_FIX = "/keyword/metrics/dummy";

    private final static String CAMPAIGN_DETAILS_POST_FIX = "/campaign/details";
    private final static String START_DATE_POST_FIX = "&startDate=";
    private final static String END_DATE_POST_FIX = "&endDate=";

    private final static String CAMPAIGN_METRICS_SAVE_FAILED = "CAMPAIGN_METRICS_SAVE_FAILED";

    private final static String ACCOUNT_DETAILS_SAVE_FAILED = "ACCOUNT_DETAILS_SAVE_FAILED";
    private final static String ACCOUNT_DETAILED_REMOVED_FAILED = "ACCOUNT_DETAILS_REMOVED_FAILED";
    private final static String CAMPAIGN_DETAILS_SAVE_FAILED = "CAMPAIGN_DETAILS_SAVE_FAILED";
    private final static String CAMPAIGN_DETAILS_REMOVED_FAILED = "CAMPAIGN_DETAILS_REMOVED_FAILED";

    private final AccountRepository accountRepository;
    private final CampaignRepository campaignRepository;

    private final MetricsRepository metricsRepository;

    private final ApiExceptionResolver resolver;

    public CachingServiceImpl(AccountRepository accountRepository,
                              CampaignRepository campaignRepository,
                              MetricsRepository metricsRepository,
                              GAMSErrorRepository gamsErrorRepository,
                              DatabaseErrorRepository databaseErrorRepository) {
        this.accountRepository = accountRepository;
        this.campaignRepository = campaignRepository;
        this.metricsRepository = metricsRepository;
        resolver = new ApiExceptionResolver(gamsErrorRepository, databaseErrorRepository);
    }

    @Override
    public void fetchAndCacheAccountData(String customerId) {
        cacheAllCampaignDetails(customerId);
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
            url = url + ADGROUP_METRICS_POST_FIX + "?adGroupId=" + resourceId + "&campaignId=" + resourceId + datePostFix;
        } else if (type == Constants.TYPE_AD) {
            url = url + AD_METRICS_POST_FIX + "?adGroupId=" + resourceId + "&adId=" + resourceId + datePostFix;
        } else if (type == Constants.TYPE_KEYWORD) {
            url = url + KEYWORD_METRICS_POST_FIX + "?adGroupId=" + resourceId + "&keywordId=" + resourceId + datePostFix;
        } else {
            System.out.println("Error");
            // TODO: Throw an exception
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
            databaseError.setErrorCode(CAMPAIGN_METRICS_SAVE_FAILED);

            resolver.throwApiException(databaseError, e.getMessage());
        }
    }

    /**
     * Cache account data from a client account
     *
     * @param customerId the id of the client account
     */
    @Override
    public void cacheAccountDetails(String customerId) {
        String url = GAMS_BASE_URL + customerId + "/account/details";
        ResponseEntity<AccountDetails> response = null;

        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.getForEntity(url, AccountDetails.class);
        } catch (Exception ex) {
            GamsError gamsError = new GamsError();
            gamsError.setCustomerId(customerId);
            gamsError.setFailedUrl(url);

            resolver.throwApiException(gamsError, ex.getMessage());
        }

        if (response == null || response.getBody() == null)
            return;

        AccountDocument accountDocument = new AccountDocument();
        accountDocument.setAccountDetails(response.getBody());
        accountDocument.setCustomerId(customerId);
        accountDocument.setLastUpdated(new Timestamp(System.currentTimeMillis()).toString());

        removeExistingAccountDocuments(customerId);

        try {
            accountRepository.save(accountDocument);
        } catch (Exception e) {
            DatabaseError databaseError = new DatabaseError();
            databaseError.setErrorCode(CAMPAIGN_DETAILS_SAVE_FAILED);
            databaseError.setFailedUrl(url);
            databaseError.setTimestamp(new Timestamp(new Date().getTime()).toString());
            databaseError.setErrorMessage(e.getMessage());
            databaseError.setStatusCode(500);

            resolver.throwApiException(databaseError, e.getMessage());
        }
    }

    /**
     * Cache all enabled and/or paused campaigns in client's account
     *
     * @param customerId the id of the client's account
     */
    @Override
    public void cacheAllCampaignDetails(String customerId) {
        String url = GAMS_BASE_URL + customerId + CAMPAIGN_DETAILS_POST_FIX;
        List<CampaignDetails> campaignDetailsList = new ArrayList<>();
        ResponseEntity<CampaignDetails[]> response;

        try {
            // fetch all campaign details from GAMS for a given customer id
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.getForEntity(url, CampaignDetails[].class);
            // extract the campaign details from the response body
            campaignDetailsList = Arrays.asList(Objects.requireNonNull(response.getBody()));
        } catch (Exception ex) {
            GamsError gamsError = new GamsError();
            gamsError.setCustomerId(customerId);
            gamsError.setFailedUrl(url);

            resolver.throwApiException(gamsError, ex.getMessage());
        }

        // extract the campaign documents into a list
        List<CampaignDocument> campaignDocumentList = new ArrayList<>();
        campaignDetailsList.forEach((campaignDetails -> {
            CampaignDocument campaignDocument = new CampaignDocument();
            campaignDocument.setCampaignDetails(campaignDetails);
            campaignDocument.setCustomerId(customerId);
            campaignDocument.setLastUpdated(new Timestamp(System.currentTimeMillis()).toString());

            campaignDocumentList.add(campaignDocument);
        }));

        // remove older versions of campaign documents from the DB
        removeExistingCampaignDocuments(customerId, campaignDetailsList);

        try {
            // save campaign documents into Mongo database
            campaignRepository.saveAll(campaignDocumentList);
        } catch (Exception e) {
            DatabaseError databaseError = new DatabaseError();
            databaseError.setErrorCode(CAMPAIGN_DETAILS_SAVE_FAILED);
            databaseError.setFailedUrl(url);
            databaseError.setTimestamp(new Timestamp(new Date().getTime()).toString());
            databaseError.setErrorMessage(e.getMessage());
            databaseError.setStatusCode(500);

            resolver.throwApiException(databaseError, e.getMessage());
        }
    }

    /**
     * Remove any AccountDocument from the Mongo DB that exist in a given list of CampaignDetails.
     *
     * @param customerId the customerId of the Google Ads account
     */
    private void removeExistingAccountDocuments(String customerId) {
        try {
            AccountDocument accountDocument = accountRepository.findAccountDocumentByCustomerId(customerId);

            // if an account document already exists in the collection remove it
            if (accountDocument != null)
                accountRepository.delete(accountDocument);

        } catch (Exception e) {
            DatabaseError databaseError = new DatabaseError();
            databaseError.setStatusCode(500);
            databaseError.setErrorMessage(e.getMessage());
            databaseError.setFailedUrl("");
            databaseError.setErrorCode(ACCOUNT_DETAILED_REMOVED_FAILED);

            resolver.throwApiException(databaseError, e.getMessage());
        }
    }

    /**
     * Remove any CampaignDocument from the Mongo DB that exist in a given list of CampaignDetails.
     *
     * @param customerId          the customerId of the Google Ads account
     * @param campaignDetailsList a list of campaign details to be removed if existing in the DB
     */
    private void removeExistingCampaignDocuments(String customerId,
                                                 List<CampaignDetails> campaignDetailsList) {
        try {
            // fetch all CampaignDocuments by the given customerId
            List<CampaignDocument> campaignDocuments
                    = campaignRepository.findAllCampaignDocumentsByCustomerId(customerId);

            // if there is exists a campaignDocument containing a campaignDetails object with the same
            // campaign resource name as in our given campaignDetailsList, add to a list to be removed
            List<CampaignDocument> documentsToBeRemoved = new ArrayList<>();
            campaignDetailsList.forEach(campaignDetails -> {
                campaignDocuments.forEach(campaignDocument -> {
                    if (campaignDetails.getCampaignResourceName()
                            .equals(campaignDocument.getCampaignDetails().getCampaignResourceName())) {
                        documentsToBeRemoved.add(campaignDocument);
                    }
                });
            });

            // remove any matching documents from the database
            if (!documentsToBeRemoved.isEmpty()) {
                campaignRepository.deleteAll(documentsToBeRemoved);
            }
        } catch (Exception e) {
            DatabaseError databaseError = new DatabaseError();
            databaseError.setStatusCode(500);
            databaseError.setErrorMessage(e.getMessage());
            databaseError.setFailedUrl("");
            databaseError.setErrorCode(CAMPAIGN_DETAILS_REMOVED_FAILED);

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

        // TODO throw an exception if a database failure occurs
        if (!metricsDocumentsToBeRemoved.isEmpty())
            metricsRepository.deleteAll(metricsDocumentsToBeRemoved);
    }
}
