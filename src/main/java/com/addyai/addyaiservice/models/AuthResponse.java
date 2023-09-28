package com.addyai.addyaiservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String access_token;

    private Long expires_in;

    private String token_type;

    private String scope;

    private String refresh_token;
}
