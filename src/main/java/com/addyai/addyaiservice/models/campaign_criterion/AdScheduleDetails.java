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

package com.addyai.addyaiservice.models.campaign_criterion;

public class AdScheduleDetails extends CriterionDetails {
    /**
     * The day of the week the ad scheduling is targeting.
     * Defaults to DayOfWeekEnum.DayOfWeek.THURSDAY_VALUE
     */
    private int dayOfWeek;

    /**
     * The hour the ad schedule should start targeting.
     * Must be a value between 0 and 23
     * Defaults to 0
     */
    private int startHour;

    /**
     * The hour the ad scheduling should stop targeting.
     * Must be a value between 0 and 23
     * Defaults to 23
     */
    private int endHour;

    /**
     * The minute the ad scheduling should start targeting.
     * Defaults to MinuteOfHourEnum.MinuteOfHour.ZERO_VALUE
     */
    private int startMinute;

    /**
     * The minute the ad scheduling should stop targeting..
     * Defaults to MinuteOfHourEnum.MinuteOfHour.FORTY_FIVE_VALUE
     */
    private int endMinute;

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }
}
