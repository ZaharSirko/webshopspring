//package com.example.webshopspring.controllers;
//
//import com.example.webshopspring.config.JwtProvider;
//import com.example.webshopspring.model.Role;
//import com.example.webshopspring.model.User;
//import com.example.webshopspring.response.AuthResponse;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
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
//import static com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier.*;
//import static org.springframework.security.oauth2.common.util.OAuth2Utils.CLIENT_ID;
//
//@RestController
//@RequestMapping("/oauth2")
//public class OAuth2Controller {
//    private static final HttpTransport transport = new NetHttpTransport();
//    private static final JsonFactory jsonFactory = new JacksonFactory();
//   @PostMapping("/google")
//   public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload) {
//       String token = payload.get("token");
//        GoogleIdToken idToken = null;
//       GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//               .setAudience(Collections.singletonList(CLIENT_ID))
//               .build();
//        try {
//           idToken =  verifier.verify(token);
//       } catch (Exception e) {
//           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//       }
//
//       GoogleIdToken.Payload googlePayload = idToken.getPayload();
//       String email = googlePayload.getEmail();
//
//        User user = userRepository.findByEmail(email).orElse(null);
//
//
//       String jwt = JwtProvider.generateToken(email);
//       return ResponseEntity.ok(new AuthResponse(jwt));
//   }
//}
//
