package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.ConversionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ConversionRepository extends MongoRepository<ConversionDocument, String> {
    @Query("{'customerId' : '?0'}")
    List<ConversionDocument> findAllConversionDocuments(String customerId);
}
