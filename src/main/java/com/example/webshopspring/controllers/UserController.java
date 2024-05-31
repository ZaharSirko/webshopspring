package com.example.webshopspring.controllers;



import com.example.webshopspring.DTO.UserDTO;
import com.example.webshopspring.config.JwtProvider;
import com.example.webshopspring.response.AuthResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.webshopspring.model.User;
import com.example.webshopspring.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import static org.springframework.security.oauth2.common.util.OAuth2Utils.CLIENT_ID;


@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        super();
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sign-in")
    public void registration() {

    }


    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();

        UserDTO isEmailExist = userService.getUserByEmail(email);
        if (isEmailExist != null) {
            throw new Exception("Email Is Already Used With Another Account");
        }
        userService.saveUser(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);


        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

    }



    @GetMapping("/log-in")
    public void log_in() {
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody User loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(username+"-------"+password);
        try {
        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception e) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Login failed: " + e.getMessage());
            authResponse.setStatus(false);
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }
    }
    
    private static final HttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new JacksonFactory();
    
//    @PostMapping("/oauth2/authorization/google")
//    public ResponseEntity<AuthResponse> googleLogin(@RequestBody Map<String, String> payload) {
//        String googletoken = payload.get("token");
//        GoogleIdToken idToken = null;
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//                .setAudience(Collections.singletonList(CLIENT_ID))
//                .build();
//        try {
//            idToken =  verifier.verify(googletoken);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        GoogleIdToken.Payload googlePayload = idToken.getPayload();
//        String email = googlePayload.getEmail();
//
//        try {
//            UserDTO user = userService.getUserByEmail(email);
//            Authentication authentication = authenticate(user.getEmail(),user.getPassword());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            String token = JwtProvider.generateToken(authentication);
//            AuthResponse authResponse = new AuthResponse();
//
//            authResponse.setMessage("Login success");
//            authResponse.setJwt(token);
//            authResponse.setStatus(true);
//
//            return new ResponseEntity<>(authResponse, HttpStatus.OK);
//        } catch (Exception e) {
//            AuthResponse authResponse = new AuthResponse();
//            authResponse.setMessage("Login failed: " + e.getMessage());
//            authResponse.setStatus(false);
//            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
//        }
//
//    }


    private Authentication authenticate(String username, String password) {

        System.out.println(username+"---++----"+password);

        UserDetails userDetails = userService.loadUserByUsername(username);

        System.out.println("Sig in in user details"+ userDetails);

        if(userDetails == null) {
            System.out.println("Sign in details - null");

            throw new BadCredentialsException("Invalid username and password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch"+userDetails);

            throw new BadCredentialsException("Invalid password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }

    @GetMapping("/logout")
    public Authentication logout(HttpServletRequest request, HttpServletResponse response) {
      return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(Principal principal) {
        String username = principal.getName();
        UserDTO user = userService.getUserByEmail(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


//    @GetMapping(value = "/profile/{email}/edit")
//    public User getUserProfileEdit(@PathVariable String email) {
//       return userService.getUserByEmail(email);
//    }
    @PostMapping("/profile/{email}/edit")
    public boolean editUser(@PathVariable("email") String email, @ModelAttribute("updatedUser") User updatedUser) {
       return userService.updateUser(email, updatedUser);
    }

}

    
