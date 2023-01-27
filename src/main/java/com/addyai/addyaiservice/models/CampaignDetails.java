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

import com.addyai.addyaiservice.models.campaign_criterion.CriterionDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CampaignDetails {

    /**
     * The identifier for the campaign. Auto-generates when the campaign is created.
     */
    private long campaignId = 0L;

    /**
     * The name of the campaign.
     */
    private String campaignName = "";

    /**
     * The resource name of the campaign. Auto-generates when the campaign is created.
     * Format: <b>/customers/{customerId}/campaigns/{campaignId}</b>
     */
    private String campaignResourceName = "";

    /**
     * The current status of the campaign.
     * This status can be "ENABLED", "PAUSED" or "REMOVED".
     * Defaults to "PAUSED"
     */
    private int status;

    /**
     * The advertising channel type for the campaign.
     */
    private int advertisingChannelType;

    /**
     * The setting for including the showing of ads to those who are located within
     * or interested in the campaign's targeted areas or only located within the targeted area.
     * Defaults to PositiveGeoTargetTypeEnum.PositiveGeoTargetType.PRESENCE_OR_INTEREST_VALUE
     */
    private int positiveGeoTargetType;

    /**
     * The setting for excluding the showing of ads to those who are located within
     * or interested in the campaign's targeted areas or only located within the targeted area.
     * Defaults to NegativeGeoTargetTypeEnum.NegativeGeoTargetType.PRESENCE
     */
    private int negativeGeoTargetType;

    /**
     * The setting for enabling/disabling enhanced cost-per-click for the campaign.
     * Defaults to false
     */
    private boolean isEnhancedCpcEnabled = false;

    /**
     * The start date of the campaign.
     * Format: yyyy-MM-dd
     * Defaults to current date
     */
    private String startDate;

    /**
     * The end date of the campaign.
     * Format: yyyy-MM-dd
     * Defaults to current date + 10 years
     */
    private String endDate;

    /**
     * Enabled if the campaign is to show ads on Google partner sites.
     * If enabled, isTargetingGoogleSearchNetwork must also be true
     * Defaults to false
     */
    private boolean isTargetingSearchNetwork = false;

    /**
     * Enabled if the campaign is to show ads on the Google Display network.
     * Defaults to false
     */
    private boolean isTargetingContentNetwork = false;

    /**
     * Enabled if the campaign will show ads on Google's search engine.
     * Defaults to true
     */
    private boolean isTargetingGoogleSearchNetwork = true;

    /**
     * The name of the budget resource. Auto-generates when the budget is created.
     * Format: <b>/customers/{customerId}/campaignBudgets/{campaignBudgetId}</b>
     */
    private String budgetResourceName = "";

    /**
     * The details of the budget for the campaign
     *
     * @see BudgetDetails
     */
    private BudgetDetails budgetDetails = new BudgetDetails();

    /**
     * The details of the targeting criterion for the campaign
     *
     * @see CriterionDetails
     */
    private List<CriterionDetails> campaignCriteriaList = new ArrayList<>();
}
