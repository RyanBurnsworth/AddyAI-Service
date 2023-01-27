package com.addyai.addyaiservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdSchedulingDetails {
    private int dayOfWeek;
    private int startHour;
    private int endHour;
    private int startMinute;
    private int endMinute;
}
