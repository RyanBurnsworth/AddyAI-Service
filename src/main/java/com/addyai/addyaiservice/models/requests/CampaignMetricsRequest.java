package com.addyai.addyaiservice.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignMetricsRequest {
    String campaignId;

    String startDate;

    String endDate;
}
