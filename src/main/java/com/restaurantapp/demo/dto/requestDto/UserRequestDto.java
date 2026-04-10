package com.restaurantapp.demo.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.restaurantapp.demo.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank
    @JsonAlias("passwordHash")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,12}$",
            message = "Password must be 8-12 characters and include uppercase, lowercase, number, and special character (@#$%^&+=!)"
    )
    private String password;

    @NotBlank
    @Email
    private String email;

    private Role roles;
}
