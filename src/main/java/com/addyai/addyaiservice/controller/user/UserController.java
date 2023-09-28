package com.addyai.addyaiservice.controller.user;

import com.addyai.addyaiservice.models.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<String> setAuthorization(String code, String userId);
    ResponseEntity<String> setTokens(AuthResponse response);
}
