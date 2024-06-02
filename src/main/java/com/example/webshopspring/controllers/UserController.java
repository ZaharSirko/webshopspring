package com.example.webshopspring.controllers;



import com.example.webshopspring.DTO.UserDTO;
import com.example.webshopspring.config.JwtProvider;
import com.example.webshopspring.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import com.example.webshopspring.model.User;
import com.example.webshopspring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;



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
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request,response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(Principal principal) {
        String username = principal.getName();
        UserDTO user = userService.getUserByEmail(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/profile/status")
    public ResponseEntity<Boolean> getStatus(Principal principal) {
        String username = principal.getName();
        if (username != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/profile/edit")
    public ResponseEntity<Boolean> editUser( Principal principal, @RequestBody User updatedUser) {
        String username = principal.getName();
       boolean isupdated = userService.updateUser(username, updatedUser);
        return new ResponseEntity<>(isupdated, HttpStatus.OK);
    }

}

    
