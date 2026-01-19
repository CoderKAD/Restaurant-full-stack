package com.example.restaurant.dto.request;

import com.example.restaurant.entity.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;

    @NotBlank
    @Pattern(regexp = "\\d{9}")
    private String phone;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
    private Role roles;

}
