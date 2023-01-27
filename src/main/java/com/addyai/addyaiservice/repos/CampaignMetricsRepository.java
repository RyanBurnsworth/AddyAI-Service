package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.metrics.CampaignMetricsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface CampaignMetricsRepository extends MongoRepository<CampaignMetricsDocument, String> {
    @Query("{'resourceId': ?0, 'date' : { $gte: ?1, $lte: ?2 } }")
    List<CampaignMetricsDocument> findAllMetricsInDateRange(String campaignId, Date startDate, Date endDate);
}
