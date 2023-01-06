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

public class NegativeKeywordDetails extends CriterionDetails {
    /**
     * The text to be targeted as a negative keyword
     */
    private String keywordText = "";

    /**
     * The match type of the negative keyword
     */
    private int keywordMatchType;

    public String getKeywordText() {
        return keywordText;
    }

    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }

    public int getKeywordMatchType() {
        return keywordMatchType;
    }

    public void setKeywordMatchType(int keywordMatchType) {
        this.keywordMatchType = keywordMatchType;
    }
}
