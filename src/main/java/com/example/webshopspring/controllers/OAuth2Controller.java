//package com.example.webshopspring.controllers;
//
//import com.example.webshopspring.config.JwtProvider;
//import com.example.webshopspring.model.Role;
//import com.example.webshopspring.model.User;
//import com.example.webshopspring.response.AuthResponse;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Collections;
//import java.util.Map;
//import java.util.UUID;
//
//import static org.springframework.security.oauth2.common.util.OAuth2Utils.CLIENT_ID;
//
//@RestController
//@RequestMapping("/oauth2")
//public class OAuth2Controller {
//
//    @PostMapping("/google")
//    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload) {
//        String token = payload.get("token");
//        GoogleIdToken idToken = null;
//        try {
//            idToken = GoogleIdTokenVerifier.verify(token);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        GoogleIdToken.Payload googlePayload = idToken.getPayload();
//        String email = googlePayload.getEmail();
//
//        User user = userRepository.findByEmail(email).orElse(null);
//        if (user == null) {
//            user = new User();
//            user.setEmail(email);
//            user.setPassword(encoder().encode(UUID.randomUUID().toString()));
//            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
//            userRepository.save(user);
//        }
//
//        String jwt = JwtProvider.generateTokenWithUsername(email);
//        return ResponseEntity.ok(new AuthResponse(jwt));
//    }
//}
//
