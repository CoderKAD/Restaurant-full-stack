package com.restaurantapp.demo.dto.requestDto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffRequestDto {
    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must be at most 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must be at most 100 characters")
    private String lastName;

    @NotNull(message = "Salary is required")
    @PositiveOrZero(message = "Salary must be greater than or equal to 0")
    private Double salary;

    @NotBlank(message = "Position is required")
    @Size(max = 100, message = "Position must be at most 100 characters")
    private String position;

    @NotNull(message = "Date joined is required")
    private LocalDate dateJoined;
    private LocalDate dateLeft;

    @NotBlank(message = "CIN is required")
    @Size(max = 20, message = "CIN must be at most 20 characters")
    private String cin;

    private UUID userId;

    @AssertTrue(message = "Date left must be on or after date joined")
    public boolean isDateLeftValid() {
        return dateLeft == null || dateJoined == null || !dateLeft.isBefore(dateJoined);
    }
}
