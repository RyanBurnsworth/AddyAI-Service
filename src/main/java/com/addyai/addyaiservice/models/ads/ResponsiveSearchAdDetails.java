package com.addyai.addyaiservice.models.ads;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponsiveSearchAdDetails extends AdDetails {
    private List<String> headlines;
    private List<String> descriptions;
    private List<String> paths;
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
}
