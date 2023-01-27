package com.addyai.addyaiservice.models.ads;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResponsiveSearchAdDetails.class, name = "responsive")
})
@Getter
@Setter
public abstract class AdDetails {
    private long adId;
    private String adType;
    private String adName;
    private String adGroupResourceName;
    private int adStatus;
}
