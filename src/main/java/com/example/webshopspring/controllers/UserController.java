package com.example.webshopspring.controllers;



import com.example.webshopspring.config.JwtProvider;
import com.example.webshopspring.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.webshopspring.model.User;
import com.example.webshopspring.service.TokenService;
import com.example.webshopspring.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.security.Principal;


@RestController
@CrossOrigin("http://localhost:3000")
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

        User isEmailExist = userService.getUserByEmail(email);
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




    private Authentication authenticate(String username, String password) {

        System.out.println(username+"---++----"+password);

        UserDetails userDetails = userService.loadUserByUsername(username);

        System.out.println("Sig in in user details"+ userDetails);

        if(userDetails == null) {
            System.out.println("Sign in details - null" + userDetails);

            throw new BadCredentialsException("Invalid username and password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch"+userDetails);

            throw new BadCredentialsException("Invalid password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }

//	record LoginRequest(String username, String password) {};
//	record LoginResponse(String message, String access_jwt_token, String refresh_jwt_token) {};
//	@PostMapping("/log-in")
//	public LoginResponse login(@RequestBody LoginRequest request) {
//
//		UsernamePasswordAuthenticationToken authenticationToken =
//				new UsernamePasswordAuthenticationToken(request.username, request.password);
//		Authentication auth = authManager.authenticate(authenticationToken);
//
//		User user = (User) userService.loadUserByUsername(request.username);
//		String access_token = tokenService.generateAccessToken(user);
//		String refresh_token = tokenService.generateRefreshToken(user);
//
//		return new LoginResponse("User with email = "+ request.username + " successfully logined!"
//
//				, access_token, refresh_token);
//	}
//	record RefreshTokenResponse(String access_jwt_token, String refresh_jwt_token) {};
//	@GetMapping("/token/refresh")
//	public RefreshTokenResponse refreshToken(HttpServletRequest request) {
//		 String headerAuth = request.getHeader("Authorization");
//		 String refreshToken = headerAuth.substring(7, headerAuth.length());
//
//		String email = tokenService.parseToken(refreshToken);
//		User user = (User) userService.loadUserByUsername(email);
//		String access_token = tokenService.generateAccessToken(user);
//		String refresh_token = tokenService.generateRefreshToken(user);
//
//		return new RefreshTokenResponse(access_token, refresh_token);
//	}


    @GetMapping("/logout")
    public Authentication logout(HttpServletRequest request, HttpServletResponse response) {
      return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByEmail(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping(value = "/profile/{email}/edit")
    public User getUserProfileEdit(@PathVariable String email) {
       return userService.getUserByEmail(email);
    }
    @PostMapping("/profile/{email}/edit")
    public boolean editUser(@PathVariable("email") String email, @ModelAttribute("updatedUser") User updatedUser) {
       return userService.updateUser(email, updatedUser);
    }

}

    
