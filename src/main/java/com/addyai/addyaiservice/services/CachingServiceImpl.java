package com.addyai.addyaiservice.services;

import com.addyai.addyaiservice.exception.DatabaseException;
import com.addyai.addyaiservice.exception.GAMSException;
import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.documents.CampaignDocument;
import com.addyai.addyaiservice.models.documents.CampaignMetricsDocument;
import com.addyai.addyaiservice.repos.CampaignMetricsRepository;
import com.addyai.addyaiservice.repos.CampaignRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CachingServiceImpl implements CachingService {
    private final static String GAMS_BASE_URL = "http://localhost:8080/api/v1/";
    private final static String CAMPAIGN_METRICS_POST_FIX = "/campaign/metrics/dummy?campaignId=";
    private final static String START_DATE_POST_FIX = "&startDate=";
    private final static String END_DATE_POST_FIX = "&endDate=";

    private final static String FETCH_CAMPAIGN_DETAILS_URL = "http://localhost:8080/api/v1/9059845250/campaign/details";

    private final CampaignRepository campaignRepository;

    private final CampaignMetricsRepository campaignMetricsRepository;

    public CachingServiceImpl(CampaignRepository campaignRepository, CampaignMetricsRepository campaignMetricsRepository) {
        this.campaignRepository = campaignRepository;
        this.campaignMetricsRepository = campaignMetricsRepository;
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
        // construct the url given the parameters
        String url = GAMS_BASE_URL + customerId + CAMPAIGN_METRICS_POST_FIX + campaignId +
                START_DATE_POST_FIX + startDate + END_DATE_POST_FIX + endDate;

        // fetch campaign metrics from GAMS for a given customer id and date range
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CampaignMetricsDocument[]> response =
                restTemplate.getForEntity(url, CampaignMetricsDocument[].class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new GAMSException("GAMS Error: " + response);
        }

        // extract the response body
        List<CampaignMetricsDocument> campaignMetricsDocuments =
                Arrays.asList(Objects.requireNonNull(response.getBody()));

        System.out.println("CODE: " + campaignMetricsDocuments.get(0).getCampaignResourceName());
        try {
            // store the campaign metrics in the collection
            campaignMetricsRepository.saveAll(campaignMetricsDocuments);
        } catch (Exception e) {
            throw new DatabaseException("Error storing campaign metrics in collection. Message: " + e);
        }
    }

    @Override
    public void cacheAllCampaignDetails(String customerId) {
        // fetch all campaign details from GAMS for a given customer id
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CampaignDetails[]> response =
                restTemplate.getForEntity(FETCH_CAMPAIGN_DETAILS_URL, CampaignDetails[].class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new GAMSException("GAMS Error: " + response);
        }

        // extract the campaign details from the response body
        List<CampaignDetails> campaignDetailsList
                = Arrays.asList(Objects.requireNonNull(response.getBody()));

        // extract the campaign documents into a list
        List<CampaignDocument> campaignDocumentList = new ArrayList<>();
        campaignDetailsList.forEach((campaignDetails -> {
            CampaignDocument campaignDocument = new CampaignDocument();
            campaignDocument.setCampaignDetails(campaignDetails);
            campaignDocument.setCustomerId(customerId);
            campaignDocument.setLastUpdated(new Timestamp(System.currentTimeMillis()).toString());

            campaignDocumentList.add(campaignDocument);
        }));

        try {
            // remove older versions of campaign documents from the DB
            removeExistingCampaignDocuments(customerId, campaignDetailsList);

            // save campaign documents into Mongo database
            campaignRepository.saveAll(campaignDocumentList);
        } catch (Exception e) {
            throw new DatabaseException("Error storing campaign details in collection. Message: " + e);
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

        try {
            // remove any matching documents from the database
            if (!documentsToBeRemoved.isEmpty()) {
                campaignRepository.deleteAll(documentsToBeRemoved);
            }
        } catch (Exception e) {
            throw new DatabaseException("Error deleting campaign details. Message: " + e);
        }
    }
}
