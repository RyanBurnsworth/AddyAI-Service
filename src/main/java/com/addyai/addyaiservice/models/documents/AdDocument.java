package com.addyai.addyaiservice.models.documents;

import com.addyai.addyaiservice.models.ads.AdDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("Ad_Details")
public class AdDocument {
    @Id
    private String id;

    private String customerId;

    private AdDetails adDetails;

    private String lastUpdated;
}
