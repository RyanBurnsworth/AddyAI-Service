package com.addyai.addyaiservice.models.documents.users;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document("User_Account")
public class UserAccountDocument {
    @Id
    private String id;

    private long customerId;

    private String businessName;

    private String website;

    private String mccCustomerId;

    private String goal;

    private Date createdAt;

    private Date updatedAt;

    private String refreshToken;

    private String accessToken;
}
