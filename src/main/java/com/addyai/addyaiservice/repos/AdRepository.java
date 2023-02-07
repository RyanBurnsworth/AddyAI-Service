package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.AdDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends MongoRepository<AdDocument, String> {
    @Query("{'customerId' : '?0', 'adDetails.adGroupResourceName' : '?1'}")
    List<AdDocument> findAllAdGroupDocumentsByAdGroup(String customerId, String adGroupResourceName);
}
