package com.addyai.addyaiservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Block {
    private AggregatedMetrics overall;
    private List<AggregatedMetrics> topCampaigns;
    private List<AggregatedMetrics> topAdGroups;
    private List<AggregatedMetrics> topAds;
    private List<AggregatedMetrics> topKeywords;
    private AggregatedMetrics deviceMetrics;
    private BaseDetails resourceDetails;
}
