package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.KeywordDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends MongoRepository<KeywordDocument, String> {
    @Query("{'customerId' : '?0', 'keywordDetails.adGroupResourceName' : '?1'}")
    List<KeywordDocument> findAllKeywordDocumentsByAdGroup(String customerId, String adGroupResourceName);
}
