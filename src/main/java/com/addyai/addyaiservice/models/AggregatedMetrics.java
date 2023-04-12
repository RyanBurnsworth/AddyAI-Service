package com.addyai.addyaiservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregatedMetrics {
    private String customerId;
    private String resourceId;
    private String parentId;
    private String resourceName;
    private long totalClicks = 0L;
    private long totalImpressions = 0L;
    private double averageCtr = 0.0;
    private int averageQualityScore = 0;
    private double averageCpc = 0.0;
    private double totalCost = 0.0;
    private double totalConversions = 0.0;
    private double avgCostPerConversion = 0.0;
    private double totalConversionValue = 0.0;
    private double avgInvalidClickRate = 0.0;
    private long totalInvalidClicks = 0L;
    private long totalPhoneCalls = 0L;
    private long totalPhoneImpressions = 0L;
    private double averagePhoneThroughRate = 0.0;
    private String lastUpdated;
}
