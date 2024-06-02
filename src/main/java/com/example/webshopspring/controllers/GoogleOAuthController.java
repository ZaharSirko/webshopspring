package com.example.webshopspring.controllers;

import com.example.webshopspring.config.JwtProvider;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GoogleOAuthController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @PostMapping("/oauth2/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> request) {
        String idTokenString = request.get("token");

        GoogleIdTokenVerifier verifier;
        try {
            verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                // You can also get other information about the user here

                // Generate JWT token
                String jwt = JwtProvider.generateToken(new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));

                Map<String, String> response = new HashMap<>();
                response.put("jwt", jwt);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(400).body("Invalid ID token.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error.");
        }
    }
}
