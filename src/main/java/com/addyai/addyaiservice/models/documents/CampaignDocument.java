package com.addyai.addyaiservice.models.documents;

import com.addyai.addyaiservice.models.CampaignDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document("Campaign_Details")
public class CampaignDocument {
    @Id
    private String id;

    private String customerId;

    private CampaignDetails campaignDetails;

    /**
     * The goal of the campaign: Clicks, Conversions, Awareness
     */
    private String goal;

    private String lastUpdated;

}
