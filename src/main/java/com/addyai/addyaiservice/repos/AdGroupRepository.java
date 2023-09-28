package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.AdGroupDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AdGroupRepository extends MongoRepository<AdGroupDocument, String> {
    @Query("{'customerId' : '?0', 'adGroupDetails.campaignResourceName' : '?1'}")
    List<AdGroupDocument> findAllAdGroupDocumentsByCampaign(String customerId, String campaignResName);

    @Query("{'customerId' : '?0', 'adGroupDetails.adGroupResourceName' : '?1'}")
    AdGroupDocument findAdGroupDocumentByResourceName(String customerId, String resourceName);
}
