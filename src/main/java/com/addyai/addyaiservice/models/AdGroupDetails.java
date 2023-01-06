/*
 * Copyright (c) 2022.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. This program
 * is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 *
 */

package com.addyai.addyaiservice.models;

public class AdGroupDetails {
    /**
     * The identifier value for the AdGroup
     */
    private long adGroupId;

    /**
     * The name of the AdGroup
     */
    private String adGroupName;

    /**
     * The resource name of the AdGroup
     */
    private String adGroupResourceName;

    /**
     * The Campaign resource name the AdGroup is associated to
     */
    private String campaignResourceName;

    /**
     * The type of AdGroup
     *
     */
    private int type;

    /**
     * The status of the adgroup
     *
     */
    private int status;

    /**
     * The maximum bid for each cost-per-click
     */
    private double cpcBid;

    public long getAdGroupId() {
        return adGroupId;
    }

    public void setAdGroupId(long adGroupId) {
        this.adGroupId = adGroupId;
    }

    public String getAdGroupName() {
        return adGroupName;
    }

    public void setAdGroupName(String adGroupName) {
        this.adGroupName = adGroupName;
    }

    public String getAdGroupResourceName() {
        return adGroupResourceName;
    }

    public void setAdGroupResourceName(String adGroupResourceName) {
        this.adGroupResourceName = adGroupResourceName;
    }

    public String getCampaignResourceName() {
        return campaignResourceName;
    }

    public void setCampaignResourceName(String campaignResourceName) {
        this.campaignResourceName = campaignResourceName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getCpcBid() {
        return cpcBid;
    }

    public void setCpcBid(double cpcBid) {
        this.cpcBid = cpcBid;
    }
}
