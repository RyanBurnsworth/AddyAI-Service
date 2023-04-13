package com.addyai.addyaiservice.models.ads;

import com.addyai.addyaiservice.models.BaseDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public abstract class AdDetails extends BaseDetails {
    @JsonProperty
    private long adId;
    @JsonProperty
    private String adType;
    @JsonProperty
    private String adName;
    @JsonProperty
    private String parentResourceName;
    @JsonProperty
    private String adGroupResourceName;
    @JsonProperty
    private int adStatus;
    @JsonProperty
    private String type;
}
