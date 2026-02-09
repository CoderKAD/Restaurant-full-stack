package com.restaurantapp.demo.dto.requestDto;

import com.restaurantapp.demo.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String username;
    private String passwordHash;
    @NotBlank
    @Email
    private String email;
    private Role roles;
}
