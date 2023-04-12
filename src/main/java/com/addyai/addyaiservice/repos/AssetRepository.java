package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.AssetDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends MongoRepository<AssetDocument, String> {
    @Query("{'customerId' : '?0'}")
    List<AssetDocument> findAllAssetDocuments(String customerId);
}
