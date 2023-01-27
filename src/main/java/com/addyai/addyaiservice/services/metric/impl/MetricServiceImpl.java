package com.addyai.addyaiservice.services.metric.impl;

import com.addyai.addyaiservice.models.documents.MetricsDocument;
import com.addyai.addyaiservice.repos.MetricsRepository;
import com.addyai.addyaiservice.services.metric.MetricService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MetricServiceImpl implements MetricService {
    private final MetricsRepository metricsRepository;

    public MetricServiceImpl(MetricsRepository metricsRepository) {
        this.metricsRepository = metricsRepository;
    }

    /**
     * Fetch metrics by a given date range from the Metrics collection in the Mongo DB
     *
     * @param resourceId the id of the resource to fetch metrics for
     * @param startDate  the start date in the range
     * @param endDate    the end date in the range
     * @return a list of {@link MetricsDocument}
     */
    @Override
    public List<MetricsDocument> fetchMetricsByDateRange(String resourceId, String startDate, String endDate) throws ParseException {
        Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

        // TODO throw exception here if failure occurs with database
        return metricsRepository.findAllMetricsInDateRange(resourceId, sDate, eDate);
    }
}
