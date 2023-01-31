package com.addyai.addyaiservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDetails {
    String customerId;
    String resourceName;
    int status;
    String currencyCode;
    String timeZone = "";
    double optimizationScore;
    boolean isCallReportingEnabled;
    boolean isCallConversionReportingEnabled;
    String callConversionActionResourceName;
    String remarketingTag;
}
