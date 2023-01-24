package com.addyai.addyaiservice.models.documents;

import com.addyai.addyaiservice.models.AdGroupDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("AdGroup_Details")
public class AdGroupDocument {
    @Id
    private String id;

    private String customerId;

    private AdGroupDetails adGroupDetails;

    private String lastUpdated;
}
