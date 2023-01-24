package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.error.DatabaseError;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseErrorRepository extends MongoRepository<DatabaseError, String> {
}
