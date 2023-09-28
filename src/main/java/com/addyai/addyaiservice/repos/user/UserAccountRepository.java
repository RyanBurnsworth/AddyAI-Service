package com.addyai.addyaiservice.repos.user;

import com.addyai.addyaiservice.models.documents.users.UserAccountDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends MongoRepository<UserAccountDocument, String> {

}
