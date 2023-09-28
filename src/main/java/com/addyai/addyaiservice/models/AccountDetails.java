package com.addyai.addyaiservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDetails extends BaseDetails {
    String customerId;
    String resourceName;
    String descriptiveName;
    int status;
    String currencyCode;
    String timeZone = "";
    double optimizationScore;
    boolean isManager;
    boolean isCallReportingEnabled;
    boolean isCallConversionReportingEnabled;
    String callConversionActionResourceName;
    String remarketingTag;
}
