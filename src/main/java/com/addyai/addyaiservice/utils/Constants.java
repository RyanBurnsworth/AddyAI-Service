package com.addyai.addyaiservice.utils;

public class Constants {
    public final static String FETCH_CAMPAIGN_DETAILS_URL = "http://localhost:8080/api/v1/9059845250/campaign/details";

    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static int TYPE_ACCOUNT = 0;
    public final static int TYPE_CAMPAIGN = 1;
    public final static int TYPE_ADGROUP = 2;
    public final static int TYPE_AD = 3;
    public final static int TYPE_KEYWORD = 4;
    public final static int TYPE_ASSET = 5;

    // WHAT?! No 6?

    public final static int TYPE_CONVERSION = 7;
    public final static int TYPE_DEVICE_ACCOUNT = 8;
    public final static int TYPE_DEVICE_CAMPAIGN = 9;
    public final static int TYPE_DEVICE_ADGROUP = 10;
    public final static int TYPE_DEVICE_AD = 11;
    public final static int TYPE_DEVICE_KEYWORD = 12;

    public final static String GAMS_BASE_URL = "http://localhost:8080/api/v1/";
    public final static String METRICS_URL_POST_FIX = "/metrics/date/demo";

    public final static String CONST_ACCOUNT = "account";
    public final static String CONST_CAMPAIGN = "campaign";
    public final static String CONST_ADGROUP = "adgroup";
    public final static String CONST_AD = "ad";
    public final static String CONST_KEYWORD = "keyword";
    public final static String CONST_DEVICE_ACCOUNT = "device_account";
    public final static String CONST_DEVICE_CAMPAIGN = "device_campaign";
    public final static String CONST_DEVICE_ADGROUP = "device_adgroup";
    public final static String CONST_DEVICE_AD = "device_ad";
    public final static String CONST_DEVICE_KEYWORD = "device_keyword";

    public final static String ACCOUNT_DETAILS_POST_FIX = "/account/details";
    public final static String CAMPAIGN_DETAILS_POST_FIX = "/campaign/details";
    public final static String ADGROUP_DETAILS_POST_FIX = "/adgroup/details";
    public final static String AD_DETAILS_POST_FIX = "/ad/details";
    public final static String KEYWORD_DETAILS_POST_FIX = "/keyword/details";
    public final static String ASSET_DETAILS_POST_FIX = "/assets/details";
    public final static String CONVERSION_DETAILS_POST_FIX = "/conversions/details";
    public final static String METRICS_SAVE_FAILED = "METRICS_SAVE_FAILED";
    public final static String METRICS_REMOVE_FAILED = "METRICS_REMOVE_FAILED";
    public final static String DETAILS_SAVE_FAILED = "DETAILS_SAVE_FAILED";
    public final static String DETAILS_REMOVE_FAILED = "DETAILS_REMOVE_FAILED";

    public final static String START_DATE_POST_FIX = "&startDate=";
    public final static String END_DATE_POST_FIX = "&endDate=";
    public final static String CAMPAIGN_RES_PART = "/campaigns/";
    public final static String ADGROUP_RES_PART = "/adGroups/";
    public final static String CUSTOMERS_RES_PART = "customers/";
    public final static String CAMPAIGN_ID_URL_PART = "?campaignId=";
    public final static String ADGROUP_ID_URL_PART = "?adGroupId=";
    public final static String RESOURCE_ID_URL_PART = "?resourceId=";
    public final static String RESOURCE_TYPE_URL_PART = "&resourceType=";
    public final static String PARENT_RESOURCE_ID_URL_PART = "&parentResourceId=";

    public final static String METRIC_CLICKS = "clicks";
    public final static String METRIC_IMPRESSIONS = "impressions";
    public final static String METRIC_CTR = "ctr";
    public final static String METRIC_QUALITY_SCORE = "qualityScore";
    public final static String METRIC_AVERAGE_CPC = "averageCpc";
    public final static String METRIC_COST = "cost";
    public final static String METRIC_CONVERSIONS = "conversions";
    public final static String METRIC_COST_PER_CONVERSION = "costPerConversion";
    public final static String METRIC_CONVERSION_VALUE = "conversionValue";
    public final static String METRIC_INVALID_CLICKS = "invalidClicks";
    public final static String METRIC_INVALID_CLICK_RATE = "invalidClickRate";
    public final static String METRIC_PHONE_CALLS = "phoneCalls";
    public final static String METRIC_PHONE_THROUGH_RATE = "phoneThroughRate";
    public final static String METRIC_PHONE_IMPRESSIONS = "phoneImpressions";
}
