package com.addyai.addyaiservice.models.documents;

import com.addyai.addyaiservice.models.KeywordDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("Keyword_Details")
public class KeywordDocument {
    @Id
    private String id;

    private String customerId;

    private KeywordDetails keywordDetails;

    private String lastUpdated;
}
