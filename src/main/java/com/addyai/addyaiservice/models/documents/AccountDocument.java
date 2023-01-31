package com.addyai.addyaiservice.models.documents;

import com.addyai.addyaiservice.models.AccountDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("Account_Details")
public class AccountDocument {
    @Id
    private String id;

    private String customerId;

    private AccountDetails accountDetails;

    private String lastUpdated;
}
