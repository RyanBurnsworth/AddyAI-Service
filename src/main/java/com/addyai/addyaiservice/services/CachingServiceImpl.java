package com.addyai.addyaiservice.services;

import com.addyai.addyaiservice.exception.ApiExceptionResolver;
import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.documents.CampaignDocument;
import com.addyai.addyaiservice.models.documents.metrics.CampaignMetricsDocument;
import com.addyai.addyaiservice.models.error.DatabaseError;
import com.addyai.addyaiservice.models.error.GamsError;
import com.addyai.addyaiservice.repos.CampaignMetricsRepository;
import com.addyai.addyaiservice.repos.CampaignRepository;
import com.addyai.addyaiservice.repos.DatabaseErrorRepository;
import com.addyai.addyaiservice.repos.GAMSErrorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;

@Service
public class CachingServiceImpl implements CachingService {
    private final static String GAMS_BASE_URL = "http://localhost:8080/api/v1/";
    private final static String CAMPAIGN_METRICS_POST_FIX = "/campaign/metrics/dummy";
    private final static String CAMPAIGN_DETAILS_POST_FIX = "/campaign/details";
    private final static String START_DATE_POST_FIX = "&startDate=";
    private final static String END_DATE_POST_FIX = "&endDate=";

    private final static String CAMPAIGN_METRICS_SAVE_FAILED = "CAMPAIGN_METRICS_SAVE_FAILED";

    private final static String CAMPAIGN_DETAILS_SAVE_FAILED = "CAMPAIGN_DETAILS_SAVE_FAILED";
    private final static String CAMPAIGN_DETAILS_REMOVED_FAILED = "CAMPAIGN_DETAILS_REMOVED_FAILED";

    private final CampaignRepository campaignRepository;

    private final CampaignMetricsRepository campaignMetricsRepository;

    private final ApiExceptionResolver resolver;

    public CachingServiceImpl(CampaignRepository campaignRepository,
                              CampaignMetricsRepository campaignMetricsRepository,
                              GAMSErrorRepository gamsErrorRepository,
                              DatabaseErrorRepository databaseErrorRepository) {
        this.campaignRepository = campaignRepository;
        this.campaignMetricsRepository = campaignMetricsRepository;
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
     * @param campaignId the id of the campaign to fetch metrics from
     * @param startDate  the start of the date range
     * @param endDate    the end of the date range
     */
    @Override
    public void cacheCampaignMetrics(String customerId, String campaignId, String startDate, String endDate) {
        List<CampaignMetricsDocument> campaignMetricsDocuments = new ArrayList<>();
        ResponseEntity<CampaignMetricsDocument[]> response;

        // construct the url given the parameters
        String url = GAMS_BASE_URL + customerId + CAMPAIGN_METRICS_POST_FIX + "?campaignResourceName=" + campaignId +
                START_DATE_POST_FIX + startDate + END_DATE_POST_FIX + endDate;

        try {
            // fetch campaign metrics from GAMS for a given customer id and date range
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.getForEntity(url, CampaignMetricsDocument[].class);

            // extract the response body
            campaignMetricsDocuments = Arrays.asList(Objects.requireNonNull(response.getBody()));

            // add customerId to the records
            campaignMetricsDocuments.forEach(document -> document.setCustomerId(customerId));
        } catch (Exception ex) {
            GamsError gamsError = new GamsError();
            gamsError.setCustomerId(customerId);
            gamsError.setFailedUrl(url);
            gamsError.setCampaignId(campaignId);

            resolver.throwApiException(gamsError, ex.getMessage());
        }

        try {
            // store the campaign metrics in the collection
            campaignMetricsRepository.saveAll(campaignMetricsDocuments);
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
}
