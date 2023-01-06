package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.CampaignDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CampaignRepository extends MongoRepository<CampaignDocument, String> {
    @Query("{'customerId': '?0'}")
    List<CampaignDocument> findAllCampaignsByCustomerId(String customerId);

    @Query("{'customerId' : ?0, 'campaignDetails.campaignName' : ?1}")
    CampaignDocument findCampaignByName(String customerId, String campaignName);
}
