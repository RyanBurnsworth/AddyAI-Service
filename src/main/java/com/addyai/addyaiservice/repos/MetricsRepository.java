package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.MetricsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface MetricsRepository extends MongoRepository<MetricsDocument, String> {
    @Query("{'resourceId': ?0, 'date' : { $gte: ?1, $lte: ?2 } }")
    List<MetricsDocument> findAllMetricsInDateRange(String resourceId, Date startDate, Date endDate);

    @Query("{'customerId': ?0, 'type' : ?1 }")
    List<MetricsDocument> findAllMetricsByCustomerIdAndType(String customerId, int type);
}
