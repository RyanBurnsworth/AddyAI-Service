package com.addyai.addyaiservice.repos;

import com.addyai.addyaiservice.models.documents.CampaignMetricsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface CampaignMetricsRepository extends MongoRepository<CampaignMetricsDocument, String> {
    @Query("{'campaignResourceName': ?0, 'date' : { $gte: ?1, $lte: ?2 } }")
    List<CampaignMetricsDocument> findAllMetricsInDateRange(String campaignResourceName, Date startDate, Date endDate);
}
