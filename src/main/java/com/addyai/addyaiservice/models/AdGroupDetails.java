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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdGroupDetails extends BaseDetails {
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
}
