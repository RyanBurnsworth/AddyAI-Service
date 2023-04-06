package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.MetricsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface MetricsRepository extends MongoRepository<MetricsDocument, String> {
    @Query("{'customerId': ?0, 'type' : ?1 }")
    List<MetricsDocument> findAllMetricsByCustomerIdAndType(String customerId, int type);

    @Query("{'customerId': ?0, 'type' : ?1, 'date' : { $gte: ?2, $lte: ?3 } }")
    List<MetricsDocument> findMetricsByCustomerIdAndTypeWithinDateRange(String customerId, int type, Date startDate, Date endDate);

    @Query("{'customerId': ?0, 'resourceId' : ?1, 'date' : { $gte: ?2, $lte: ?3 } }")
    List<MetricsDocument> findMetricsByCustomerIdAndResourceIdWithinDateRange(String customerId, String resourceId, Date startDate, Date endDate);
}
