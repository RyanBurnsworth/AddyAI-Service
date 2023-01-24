package com.addyai.addyaiservice.models.documents;

import com.addyai.addyaiservice.models.assets.AssetDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("Asset_Details")
public class AssetDocument {
    @Id
    private String id;

    private String customerId;

    private AssetDetails assetDetails;

    private String lastUpdated;
}
