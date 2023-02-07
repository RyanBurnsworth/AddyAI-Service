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
public class BudgetDetails extends BaseDetails {
    /**
     * The identifier for the budget. Auto-generates when the budget is created.
     */
    private long budgetId;

    /**
     * The name of the budget.
     */
    private String name;

    /**
     * The budget's resource name. Auto-generates when the budget is created.
     * Format: <b>/customers/{customerId}/campaignBudgets/{campaignBudgetId}</b>
     */
    private String resourceName;

    /**
     * The ad delivery method. STANDARD spreads the showing of the campaign's ads
     * throughout the day to refrain from quickly exhausting the budget. ACCELERATED
     * will show ads at every chance until the budget is exhausted for the day.
     *
     */
    private int deliveryMethod;

    /**
     * Enabled if this budget is shared by multiple campaigns.
     * Disabled if this budget is only used by one campaign.
     */
    private boolean isShared;

    /**
     * The amount of daily budget for the campaign.
     */
    private int dailyBudgetAmount;

    /**
     * The status of the budget
     */
    private int status;
}
