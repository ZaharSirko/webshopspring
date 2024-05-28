package com.example.webshopspring.DTO;

import com.example.webshopspring.model.Role;
import com.example.webshopspring.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link User}
 */
public record UserDTO(Long id, @NotNull @NotEmpty String email, String real_name, String phoneNumber, String adress,
                      Set<Role> roles) implements Serializable {
}