package com.addyai.addyaiservice.repos.user;

import com.addyai.addyaiservice.models.documents.users.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {
}
