package com.addyai.addyaiservice.models.documents.metrics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Document("Campaign_Metrics")
public class CampaignMetricsDocument {

    private String campaignResourceName;

    private long clicks;

    private long impressions;

    private double ctr;

    private double averageCpc;

    private double cost;

    private double conversions;

    private double conversionRate;

    private double costPerConversion;

    private double conversionValue;

    private double invalidClickRate;

    private long invalidClicks;

    private long phoneCalls;

    private long phoneImpressions;

    private double phoneThroughRate;

    private Date date;
}
