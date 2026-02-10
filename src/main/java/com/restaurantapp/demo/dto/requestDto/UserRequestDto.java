package com.restaurantapp.demo.dto.requestDto;

import com.restaurantapp.demo.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,12}$",
            message = "Password must be 8-12 characters and include uppercase, lowercase, number, and special character (@#$%^&+=!)"
    )
    private String passwordHash;
    @NotBlank
    @Email
    private String email;
    private Role roles;
}
