package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.CampaignDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CampaignRepository extends MongoRepository<CampaignDocument, String> {
    @Query("{'customerId': '?0'}")
    List<CampaignDocument> findAllCampaignDocumentsByCustomerId(String customerId);

    @Query("{'customerId' : ?0, 'campaignDetails.campaignName' : ?1}")
    CampaignDocument findCampaignDocumentByName(String customerId, String campaignName);

    @Query("{'customerId' : ?0, 'campaignDetails.campaignResourceName' : ?1}")
    CampaignDocument findCampaignDocumentByResourceName(String customerId, String campaignResourceName);
}
