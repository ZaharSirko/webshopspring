package com.example.webshopspring.service;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import com.example.webshopspring.model.Role;
import com.example.webshopspring.model.User;
import com.example.webshopspring.repo.RoleRepository;
import com.example.webshopspring.repo.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserService implements UserDetailsService  {
    @PersistenceContext
    private EntityManager em;
    final private UserRepository userRepository;
    final private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Bean
   public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();                                                                     
}

@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).orElse(null);

    if (user == null) {
        throw new UsernameNotFoundException("User not found");
    }

    return user;
}

    public boolean saveUser(User user) {
         User userFromDB = userRepository.findByEmail(user.getUsername()).orElse(null);

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(encoder().encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DefaultOAuth2UserService() {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
                OAuth2User oauth2User = super.loadUser(userRequest);
                Map<String, Object> attributes = oauth2User.getAttributes();
      
                String email = (String) attributes.get("email");

                User user = userRepository.findByEmail(email).orElse(null);
                if (user == null) {
                    user = new User();
                    user.setEmail(email);
                    user.setPassword(encoder().encode(UUID.randomUUID().toString()));
                    user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
              
                    userRepository.save(user);
                }

                return new DefaultOAuth2User(
                        user.getRoles(),
                        attributes,
                        "email");

            }
        };
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


    public boolean updateUser(String email, User updatedUser) {
        User existingUser = userRepository.findByEmail(email).orElse(null);
        if (existingUser != null) {
            existingUser.setReal_name(updatedUser.getReal_name());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setAdress(updatedUser.getAdress());
            userRepository.save(existingUser); 
            return true;
        }
        return false;
    }

    
    }
    
