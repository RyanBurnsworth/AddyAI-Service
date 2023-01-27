package com.addyai.addyaiservice.models.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Document("Metrics")
public class MetricsDocument {
    @Id
    private String mongoId;
    private int type;
    private Date date;
    private String customerId;
    private String resourceId;
    private String parentId;
    private String resourceName;
    private long clicks;
    private long impressions;
    private double ctr;
    private int qualityScore;
    private double averageCpc;
    private double cost;
    private double conversions;
    private double costPerConversion;
    private double conversionValue;
    private double invalidClickRate;
    private long invalidClicks;
    private long phoneCalls;
    private long phoneImpressions;
    private double phoneThroughRate;
    private String lastUpdated;
}
