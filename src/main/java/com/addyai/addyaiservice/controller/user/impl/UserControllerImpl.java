package com.addyai.addyaiservice.controller.user.impl;

import com.addyai.addyaiservice.controller.user.UserController;
import com.addyai.addyaiservice.models.AuthResponse;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/v1/user")
public class UserControllerImpl implements UserController {
    @Override
    @GetMapping("/auth")
    public ResponseEntity<String> setAuthorization(@RequestParam String code, @RequestParam String userId) {
        if (code == null || code.isEmpty() || userId == null || userId.isEmpty()) {
            System.out.println("HEre we are ");
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", "453865601493-hjr9cbb0uslsdb5k4uuq9vaso03tpua6.apps.googleusercontent.com");
        body.add("client_secret", "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");
        body.add("redirect_uri", "http://localhost:4200/api/v1/user/auth");
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, httpHeaders);

        restTemplate.exchange("https://oauth2.googleapis.com/token", HttpMethod.POST, entity, Void.class);
        return null;
    }

    @Override
    @PostMapping("/auth")
    public ResponseEntity<String> setTokens(@RequestBody AuthResponse response) {
        System.out.println("Refresh Token" + response.getRefresh_token());
        return null;
    }
}
