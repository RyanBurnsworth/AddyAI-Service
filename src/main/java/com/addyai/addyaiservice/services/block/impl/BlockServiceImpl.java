package com.addyai.addyaiservice.services.block.impl;

import com.addyai.addyaiservice.models.AggregatedMetrics;
import com.addyai.addyaiservice.models.Block;
import com.addyai.addyaiservice.models.documents.MetricsDocument;
import com.addyai.addyaiservice.models.requests.MetricsByDateRequest;
import com.addyai.addyaiservice.services.block.BlockService;
import com.addyai.addyaiservice.services.fetch.FetchingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.addyai.addyaiservice.utils.Constants.*;

@Service
public class BlockServiceImpl implements BlockService {
    private final FetchingService fetchingService;

    public BlockServiceImpl(FetchingService fetchingService) {
        this.fetchingService = fetchingService;
    }

    @Override
    public Block fetchUIBlock(MetricsByDateRequest request) {
        switch (request.getResourceType()) {
            case CONST_ACCOUNT:
                return assembleAccountBlock(request);
            case CONST_CAMPAIGN:
                break;
            case CONST_ADGROUP:
                break;
            case CONST_AD:
                break;
            case CONST_KEYWORD:
                break;
            case CONST_DEVICE_ACCOUNT:
                break;
            case CONST_DEVICE_CAMPAIGN:
                break;
            case CONST_DEVICE_ADGROUP:
                break;
            case CONST_DEVICE_AD:
                break;
            case CONST_DEVICE_KEYWORD:
                break;
            default:
                break;
        }
        return null;
    }

    private Block assembleAccountBlock(MetricsByDateRequest request) {
        Block block = new Block();

        // fetch all campaign metrics from the database for the given date range
        List<MetricsDocument> campaignMetricsDocuments = fetchingService
                .fetchMetricsByCustomerIdAndType(request.getCustomerId(), request.getStartDate(), request.getEndDate(),
                        TYPE_CAMPAIGN);

        // fetch all adgroup metrics from the database for the given date range
        List<MetricsDocument> adGroupMetricsDocuments = fetchingService
                .fetchMetricsByCustomerIdAndType(request.getCustomerId(), request.getStartDate(), request.getEndDate(),
                        TYPE_ADGROUP);

        // fetch all ad metrics from the database for the given date range
        List<MetricsDocument> adMetricsDocuments = fetchingService
                .fetchMetricsByCustomerIdAndType(request.getCustomerId(), request.getStartDate(), request.getEndDate(),
                        TYPE_AD);

        // fetch all keyword metrics from the database for the given date range
        List<MetricsDocument> keywordMetricsDocuments = fetchingService
                .fetchMetricsByCustomerIdAndType(request.getCustomerId(), request.getStartDate(), request.getEndDate(),
                        TYPE_KEYWORD);

        // aggregate all the campaigns at once to result in an overall view of an account
        AggregatedMetrics overallAccountMetrics = aggregateMetrics(campaignMetricsDocuments);
        block.setOverall(overallAccountMetrics);

        List<AggregatedMetrics> topCampaignsByClicks = getSortedAggregatedMetricsByResource(request.getCustomerId(), "clicks",
                request.getResourceType(), request.getParentResourceId(), request.getStartDate(), request.getEndDate());
        topCampaignsByClicks = limitNumberOfAggregatedMetrics(topCampaignsByClicks, 5, true);

        List<AggregatedMetrics> topAdGroupsByClicks = getSortedAggregatedMetricsByResource(request.getCustomerId(), "clicks",
                request.getResourceType(), request.getParentResourceId(), request.getStartDate(), request.getEndDate());
        topCampaignsByClicks = limitNumberOfAggregatedMetrics(topCampaignsByClicks, 5, true);

/*
        List<AggregatedMetrics> topCampaignsByClicks = getSortedAggregatedMetricsByResource(request.getCustomerId(), "clicks",
                request.getResourceType(), request.getParentResourceId(), request.getStartDate(), request.getEndDate());
        topCampaignsByClicks = limitNumberOfAggregatedMetrics(topCampaignsByClicks, 5, true);

        List<AggregatedMetrics> topCampaignsByClicks = getSortedAggregatedMetricsByResource(request.getCustomerId(), "clicks",
                request.getResourceType(), request.getParentResourceId(), request.getStartDate(), request.getEndDate());
        topCampaignsByClicks = limitNumberOfAggregatedMetrics(topCampaignsByClicks, 5, true);

        block.setTopCampaigns(topCampaignsByClicks);
        block.setTopAdGroups(searchDocumentsForBestByTargetMetric(adGroupMetricsDocuments, "clicks", 5));
        block.setTopAds(searchDocumentsForBestByTargetMetric(adMetricsDocuments, "clicks", 5));
        block.setTopKeywords(searchDocumentsForBestByTargetMetric(keywordMetricsDocuments, "clicks", 10));
*/

        return block;
    }

    /**
     * Build OverallMetrics object containing the total sums and averages of the metrics provided in the list of MetricDocuments
     *
     * @param metricsDocuments a list of metric documents used to total and average the values within
     * @return an OverallMetrics object containing the overall metrics values for the resource
     */
    private AggregatedMetrics aggregateMetrics(List<MetricsDocument> metricsDocuments) {
        AggregatedMetrics aggregatedMetrics = new AggregatedMetrics();

        // total all the metric values within the documents
        metricsDocuments.forEach(metricsDocument -> {
            aggregatedMetrics.setTotalClicks(metricsDocument.getClicks() + aggregatedMetrics.getTotalClicks());
            aggregatedMetrics.setTotalImpressions(metricsDocument.getImpressions() + aggregatedMetrics.getTotalImpressions());
            aggregatedMetrics.setAverageCpc(metricsDocument.getAverageCpc() + aggregatedMetrics.getAverageCpc());
            aggregatedMetrics.setAverageCtr(metricsDocument.getCtr() + aggregatedMetrics.getAverageCtr());
            aggregatedMetrics.setAverageQualityScore(metricsDocument.getQualityScore() + aggregatedMetrics.getAverageQualityScore());
            aggregatedMetrics.setTotalCost(metricsDocument.getCost() + aggregatedMetrics.getTotalCost());
            aggregatedMetrics.setTotalConversions(metricsDocument.getConversions() + aggregatedMetrics.getTotalConversions());
            aggregatedMetrics.setAvgCostPerConversion(metricsDocument.getCostPerConversion() + aggregatedMetrics.getAvgCostPerConversion());
            aggregatedMetrics.setTotalConversionValue(metricsDocument.getConversionValue() + aggregatedMetrics.getTotalConversionValue());
            aggregatedMetrics.setAvgCostPerConversion(metricsDocument.getCostPerConversion() + aggregatedMetrics.getAvgCostPerConversion());
            aggregatedMetrics.setAvgInvalidClickRate(metricsDocument.getInvalidClickRate() + aggregatedMetrics.getAvgInvalidClickRate());
            aggregatedMetrics.setTotalInvalidClicks(metricsDocument.getInvalidClicks() + aggregatedMetrics.getTotalInvalidClicks());
            aggregatedMetrics.setAveragePhoneThroughRate(metricsDocument.getPhoneThroughRate() + aggregatedMetrics.getAveragePhoneThroughRate());
            aggregatedMetrics.setTotalPhoneImpressions(metricsDocument.getPhoneImpressions() + aggregatedMetrics.getTotalImpressions());
            aggregatedMetrics.setTotalPhoneCalls(metricsDocument.getPhoneCalls() + aggregatedMetrics.getTotalPhoneCalls());
        });

        // average the necessary values
        aggregatedMetrics.setAverageCpc(aggregatedMetrics.getAverageCpc() / metricsDocuments.size());
        aggregatedMetrics.setAverageCtr(aggregatedMetrics.getAverageCtr() / metricsDocuments.size());
        aggregatedMetrics.setAverageQualityScore(aggregatedMetrics.getAverageQualityScore() / metricsDocuments.size());
        aggregatedMetrics.setAvgInvalidClickRate(aggregatedMetrics.getAvgInvalidClickRate() / metricsDocuments.size());
        aggregatedMetrics.setAveragePhoneThroughRate(aggregatedMetrics.getAveragePhoneThroughRate() / metricsDocuments.size());
        aggregatedMetrics.setAvgCostPerConversion(aggregatedMetrics.getAvgCostPerConversion() / metricsDocuments.size());

        return aggregatedMetrics;
    }

    /**
     * Get a sorted {@link AggregatedMetrics} for all resources in the account
     *
     * @param customerId       the id of the customer account
     * @param targetMetric     the target metric to sort by
     * @param resourceType     the type of resource to aggregate
     * @param parentResourceId the id of the parent of the resource (if one has)
     * @param startDate        the start of the date range
     * @param endDate          the end of the date range
     * @return a sorted list of {@link AggregatedMetrics} based on all of an accounts resources
     */
    private List<AggregatedMetrics> getSortedAggregatedMetricsByResource(String customerId,
                                                                         String targetMetric,
                                                                         String resourceType,
                                                                         String parentResourceId,
                                                                         String startDate,
                                                                         String endDate) {
        List<String> resourceIds = new ArrayList<>();

        // fetch the resource ids based on the given resourceType
        switch (resourceType) {
            case CONST_CAMPAIGN -> resourceIds = fetchingService.getCampaignResourceIds(customerId);
            case CONST_ADGROUP -> resourceIds = fetchingService.getAdGroupResourceIds(customerId, parentResourceId);
            case CONST_AD -> resourceIds = fetchingService.getAdResourceIds(customerId, parentResourceId);
            case CONST_KEYWORD -> resourceIds = fetchingService.getKeywordResourceIds(customerId, parentResourceId);
        }

        HashMap<String, List<MetricsDocument>> metricsDocumentsMapByCampaign = new HashMap<>();

        // gather a mapping of each resource's metrics for the given date range and their resource id
        resourceIds.forEach(resourceId -> {
            List<MetricsDocument> documents = fetchingService
                    .fetchMetricsByCustomerIdAndResourceId(customerId, resourceId, startDate, endDate);

            metricsDocumentsMapByCampaign.put(resourceId, documents);
        });

        // extract each list of resource's metrics documents and aggregated them individually
        List<AggregatedMetrics> aggregatedMetricsList = new ArrayList<>();
        metricsDocumentsMapByCampaign.forEach((resourceId, metricsList) -> {
            AggregatedMetrics aggregatedMetrics = aggregateMetrics(metricsList);
            aggregatedMetricsList.add(aggregatedMetrics);
        });

        return sortAggregatedMetricsListByTargetMetric(aggregatedMetricsList, targetMetric);
    }

    /**
     * Sort {@link AggregatedMetrics} in ascending order by the targetMetric
     *
     * @param aggregatedMetricsList the list of aggregatedMetrics to sort
     * @param targetMetric          the target metric to sort by
     * @return a sorted list of {@link AggregatedMetrics}
     */
    private List<AggregatedMetrics> sortAggregatedMetricsListByTargetMetric(List<AggregatedMetrics> aggregatedMetricsList,
                                                                            String targetMetric) {
        List<AggregatedMetrics> selectedDocuments = new ArrayList<>();

        // we are removing an item from the list each iteration to store in selectedDocuments
        while (aggregatedMetricsList.size() > 0) {
            AggregatedMetrics selectedDocument = new AggregatedMetrics();
            for (int j = 0; j < aggregatedMetricsList.size(); j++) {

                // select the document with the highest targetMetric value
                switch (targetMetric) {
                    case METRIC_CLICKS -> {
                        if (aggregatedMetricsList.get(j).getTotalClicks() > selectedDocument.getTotalClicks()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_IMPRESSIONS -> {
                        if (aggregatedMetricsList.get(j).getTotalImpressions() > selectedDocument.getTotalImpressions()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_CTR -> {
                        if (aggregatedMetricsList.get(j).getAverageCtr() > selectedDocument.getAverageCtr()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_QUALITY_SCORE -> {
                        if (aggregatedMetricsList.get(j).getAverageQualityScore() > selectedDocument.getAverageQualityScore()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_AVERAGE_CPC -> {
                        if (aggregatedMetricsList.get(j).getAverageCpc() > selectedDocument.getAverageCpc()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_COST -> {
                        if (aggregatedMetricsList.get(j).getTotalCost() > selectedDocument.getTotalCost()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_CONVERSIONS -> {
                        if (aggregatedMetricsList.get(j).getTotalConversions() > selectedDocument.getTotalConversions()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_COST_PER_CONVERSION -> {
                        if (aggregatedMetricsList.get(j).getAvgCostPerConversion() > selectedDocument.getAvgCostPerConversion()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_CONVERSION_VALUE -> {
                        if (aggregatedMetricsList.get(j).getTotalConversionValue() > selectedDocument.getTotalConversionValue()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_INVALID_CLICK_RATE -> {
                        if (aggregatedMetricsList.get(j).getAvgInvalidClickRate() > selectedDocument.getAvgInvalidClickRate()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_INVALID_CLICKS -> {
                        if (aggregatedMetricsList.get(j).getTotalInvalidClicks() > selectedDocument.getTotalInvalidClicks()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_PHONE_CALLS -> {
                        if (aggregatedMetricsList.get(j).getTotalPhoneCalls() > selectedDocument.getTotalPhoneCalls()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_PHONE_IMPRESSIONS -> {
                        if (aggregatedMetricsList.get(j).getTotalPhoneImpressions() > selectedDocument.getTotalPhoneImpressions()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    case METRIC_PHONE_THROUGH_RATE -> {
                        if (aggregatedMetricsList.get(j).getAveragePhoneThroughRate() > selectedDocument.getAveragePhoneThroughRate()) {
                            selectedDocument = aggregatedMetricsList.get(j);
                        }
                    }
                    default -> {
                    }
                }

                if ((j + 1) == aggregatedMetricsList.size()) {
                    // add the selected document to the list
                    selectedDocuments.add(selectedDocument);

                    // remove the selectedDocument from the metrics list
                    aggregatedMetricsList.remove(selectedDocument);
                }
            }
        }
        return selectedDocuments;
    }

    /**
     * Take a specified amount {@link AggregatedMetrics} from the top or bottom of the List stack
     *
     * @param aggregatedMetricsList the list to take a subset from
     * @param limit                 the number of {@link AggregatedMetrics} to return
     * @param takeFromTop           true if you want to start from the top of the stack
     * @return a limited list of {@link AggregatedMetrics}
     */
    private List<AggregatedMetrics> limitNumberOfAggregatedMetrics(List<AggregatedMetrics> aggregatedMetricsList,
                                                                   int limit,
                                                                   boolean takeFromTop) {
        List<AggregatedMetrics> limitedAggregatedMetrics = new ArrayList<>();
        if (takeFromTop) {
            for (int i = 0; i < limit; i++) {
                limitedAggregatedMetrics.add(aggregatedMetricsList.get(i));
            }
            return limitedAggregatedMetrics;
        } else {
            // remove all the aggregatedMetrics objects in the front
            for (int i = 0; i < aggregatedMetricsList.size() - limit; i++) {
                aggregatedMetricsList.remove(aggregatedMetricsList.get(i));
            }
            // return the remaining items at the end
            return aggregatedMetricsList;
        }
    }
}
