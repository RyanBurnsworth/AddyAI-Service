package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.AccountDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<AccountDocument, String> {
    @Query("{'customerId': '?0'}")
    AccountDocument findAccountDocumentByCustomerId(String customerId);
}
