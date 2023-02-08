package com.addyai.addyaiservice.models.documents;

import com.addyai.addyaiservice.models.ConversionDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("Conversion_Details")
public class ConversionDocument {
    @Id
    private String id;

    private String customerId;

    private ConversionDetails conversionDetails;

    private String lastUpdated;
}
