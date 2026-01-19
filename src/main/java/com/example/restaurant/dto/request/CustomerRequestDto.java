package com.example.restaurant.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 30)
    String firstName ;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 30)
    String lastName;

    @NotBlank(message = "Address is required")
    @Size(min = 2, max = 150)
    String address;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\d{9}", message = "Phone must be 9 digits")
    String phone;

    @NotNull(message = "User ID is required")
    UUID userId ;
}
