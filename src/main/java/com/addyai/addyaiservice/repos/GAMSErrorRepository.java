package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.error.GamsError;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GAMSErrorRepository extends MongoRepository<GamsError, String> {
}
