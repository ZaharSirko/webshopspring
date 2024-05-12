package com.example.webshopspring.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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



@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
	private final AuthenticationManager authManager;

    @Autowired
    public UserController(TokenService tokenService, AuthenticationManager authManager,UserService userService) {
        super();
		this.tokenService = tokenService;
		this.authManager = authManager;
        this.userService = userService;
    }

    @GetMapping("/sign-in")
    public User registration() {
       return  new User();
    }


    @PostMapping("/sign-in")
    public void addUser(@ModelAttribute("user") @Valid User user) {
    }


    @GetMapping("/log-in")
    public void log_in() {
    }


	record LoginRequest(String username, String password) {};
	record LoginResponse(String message, String access_jwt_token, String refresh_jwt_token) {};
	@PostMapping("/log-in")
	public LoginResponse login(@RequestBody LoginRequest request) {

		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(request.username, request.password);
		Authentication auth = authManager.authenticate(authenticationToken);
		
		User user = (User) userService.loadUserByUsername(request.username);
		String access_token = tokenService.generateAccessToken(user);
		String refresh_token = tokenService.generateRefreshToken(user);

		return new LoginResponse("User with email = "+ request.username + " successfully logined!"
				
				, access_token, refresh_token);
	}
	record RefreshTokenResponse(String access_jwt_token, String refresh_jwt_token) {};
	@GetMapping("/token/refresh")
	public RefreshTokenResponse refreshToken(HttpServletRequest request) {
		 String headerAuth = request.getHeader("Authorization");		 
		 String refreshToken = headerAuth.substring(7, headerAuth.length());
		
		String email = tokenService.parseToken(refreshToken);
		User user = (User) userService.loadUserByUsername(email);
		String access_token = tokenService.generateAccessToken(user);
		String refresh_token = tokenService.generateRefreshToken(user);
		
		return new RefreshTokenResponse(access_token, refresh_token);
	}


    @GetMapping("/logout")
    public Authentication logout(HttpServletRequest request, HttpServletResponse response) {
      return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping(value = "/profile/{email}")
    public User getUserProfile(@PathVariable String email, Model model) {
       return userService.getUserByEmail(email);
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

    
