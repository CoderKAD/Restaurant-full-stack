package com.restaurantapp.demo.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private Double salary;
    @NotBlank
    private String position;
    private LocalDate dateJoined;
    private LocalDate dateLeft;
    @NotBlank
    private String cin;
    private UUID userId;
}
