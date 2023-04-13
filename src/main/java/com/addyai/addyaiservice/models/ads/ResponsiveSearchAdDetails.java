package com.addyai.addyaiservice.models.ads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponsiveSearchAdDetails extends AdDetails {
    @JsonProperty
    private List<String> headlines;
    @JsonProperty
    private List<String> descriptions;
    @JsonProperty
    private List<String> paths;
    @JsonProperty
    private String finalUrl;

    @Override
    public long getAdId() {
        return super.getAdId();
    }

    @Override
    public void setAdId(long adId) {
        super.setAdId(adId);
    }

    @Override
    public String getAdName() {
        return super.getAdName();
    }

    @Override
    public void setAdName(String adName) {
        super.setAdName(adName);
    }

    @Override
    public String getAdGroupResourceName() {
        return super.getAdGroupResourceName();
    }

    @Override
    public void setAdGroupResourceName(String adGroupResourceName) {
        super.setAdGroupResourceName(adGroupResourceName);
    }

    @Override
    public int getAdStatus() {
        return super.getAdStatus();
    }

    @Override
    public void setAdStatus(int adStatus) {
        super.setAdStatus(adStatus);
    }

    @Override
    public String getAdType() {
        return super.getAdType();
    }

    @Override
    public void setAdType(String adType) {
        super.setAdType(adType);
    }

    @Override
    public String getParentResourceName() {
        return super.getParentResourceName();
    }

    @Override
    public void setParentResourceName(String parentResourceName) {
        super.setParentResourceName(parentResourceName);
    }

    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }
}
