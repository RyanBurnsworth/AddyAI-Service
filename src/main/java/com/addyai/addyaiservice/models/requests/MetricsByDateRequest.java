package com.addyai.addyaiservice.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricsByDateRequest {
    private String customerId;
    private String resourceId;
    private String parentResourceId;
    private String startDate;
    private String endDate;
    private String resourceType;
}
