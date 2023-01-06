package com.addyai.addyaiservice.services;

import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.documents.CampaignDocument;
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

import static com.addyai.addyaiservice.utils.Constants.FETCH_CAMPAIGN_DETAILS_URL;

@Service
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public List<CampaignDetails> fetchAllCampaignDetails(String customerId) {
        List<CampaignDetails> campaignDetailsList = new ArrayList<>();
        List<CampaignDocument> campaignDocumentList
                = campaignRepository.findAllCampaignsByCustomerId(customerId);

        campaignDocumentList.forEach(campaignDocument -> {
            campaignDetailsList.add(campaignDocument.getCampaignDetails());
        });

        return campaignDetailsList;
    }

    @Override
    public CampaignDetails fetchCampaignDetailsByName(String customerId, String campaignName) {
        CampaignDocument campaignDocument
                = campaignRepository.findCampaignByName(customerId, campaignName);
        System.out.println("CAMPAIGN RES NAME: " + campaignDocument.getCampaignDetails().getCampaignResourceName());

        return campaignDocument.getCampaignDetails();
    }

    /**
     * Create or Update All Campaign Details records for a given customerId in the Mongo db
     *
     * @param customerId the Google Ads customerId
     */
    @Override
    public void fetchAndUpdateCache(String customerId) {
        // fetch all campaign details from GAMS for a given customer id
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CampaignDetails[]> response =
                restTemplate.getForEntity(FETCH_CAMPAIGN_DETAILS_URL, CampaignDetails[].class);

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

        // remove older versions of campaign documents from the DB
        removeExistingCampaignDocuments(customerId, campaignDetailsList);

        // save campaign documents into Mongo database
        List<CampaignDocument> campaignDocuments =
                campaignRepository.saveAll(campaignDocumentList);
    }

    public void fetchAndUpdateSingleCampaignCache(String customerId, String campaignName) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CampaignDetails> response =
                restTemplate.getForEntity(FETCH_CAMPAIGN_DETAILS_URL, CampaignDetails.class);

        CampaignDetails campaignDetails = response.getBody();
        if (campaignDetails != null && response.getStatusCode() == HttpStatus.OK) {
            // remove the existing campaign document from the db
            List<CampaignDetails> campaignDetailsList = new ArrayList<>();
            campaignDetailsList.add(campaignDetails);
            removeExistingCampaignDocuments(customerId, campaignDetailsList);

            // save the document to the database
            CampaignDocument campaignDocument = new CampaignDocument();
            campaignDocument.setCampaignDetails(campaignDetails);
            campaignDocument.setCustomerId(customerId);
            campaignDocument.setLastUpdated(new Timestamp(System.currentTimeMillis()).toString());

            campaignRepository.save(campaignDocument);
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
                = campaignRepository.findAllCampaignsByCustomerId(customerId);

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
    }
}
