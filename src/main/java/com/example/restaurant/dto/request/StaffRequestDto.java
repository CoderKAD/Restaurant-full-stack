package com.example.restaurant.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffRequestDto {
    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary must be greater than or equal to 0")
    private Double salary;

    @NotBlank(message = "Position is required")
    private String position;

    @NotNull(message = "Date joined is required")
    private LocalDate dateJoined;

    private LocalDate dateLeft;

    @NotNull(message = "User ID is required")
    private UUID userId;
}
