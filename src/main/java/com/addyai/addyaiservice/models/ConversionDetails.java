package com.addyai.addyaiservice.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversionDetails extends BaseDetails {
    private String actionId;
    private String actionName;
    private String resourceName;
    private int status;
    private int type;
    private double defaultValue;
    private String currencyCode;
    private long callDurationSeconds;

    private int actionCountingType;
    private int category;
}
