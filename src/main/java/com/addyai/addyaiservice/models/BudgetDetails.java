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

public class BudgetDetails {
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

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(int deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public int getDailyBudgetAmount() {
        return dailyBudgetAmount;
    }

    public void setDailyBudgetAmount(int dailyBudgetAmount) {
        this.dailyBudgetAmount = dailyBudgetAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
