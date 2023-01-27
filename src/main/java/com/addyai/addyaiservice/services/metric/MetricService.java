package com.addyai.addyaiservice.services.metric;

import com.addyai.addyaiservice.models.documents.MetricsDocument;

import java.text.ParseException;
import java.util.List;

public interface MetricService {
    List<MetricsDocument> fetchMetricsByDateRange(String resourceId, String startDate, String endDate) throws ParseException;
}
