package com.addyai.addyaiservice.models.documents.users;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document("User")
public class UserDocument {
    @Id
    private String id;

    private String userId;

    private String name;

    private String email;

    private String authToken;

    private Date createdAt;

    private Date updatedAt;

    private Date billingDate;

    private Date lastBillingDate;

    private double amountPaid;

    private double amountDue;

    private int planType;
}
