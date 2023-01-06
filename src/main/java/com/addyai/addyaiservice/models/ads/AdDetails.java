package com.addyai.addyaiservice.models.ads;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResponsiveSearchAdDetails.class, name = "responsive")
})
public abstract class AdDetails {
    private long adId;

    private String adType;
    private String adName;

    private String adGroupResourceName;

    private int adStatus;

    public long getAdId() {
        return adId;
    }

    public void setAdId(long adId) {
        this.adId = adId;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdGroupResourceName() {
        return adGroupResourceName;
    }

    public void setAdGroupResourceName(String adGroupResourceName) {
        this.adGroupResourceName = adGroupResourceName;
    }

    public int getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(int adStatus) {
        this.adStatus = adStatus;
    }
}
