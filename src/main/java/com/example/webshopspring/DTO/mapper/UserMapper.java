package com.example.webshopspring.DTO.mapper;


import com.example.webshopspring.DTO.UserDTO;
import com.example.webshopspring.model.Role;
import com.example.webshopspring.model.User;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserMapper {
    public static UserDTO toUserDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getReal_name(),
                user.getPhoneNumber(),
                user.getAdress(),
                user.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList()),
                user.getUsername());
    }

    public static User toUserId(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.id());
        return user;
    }
}
