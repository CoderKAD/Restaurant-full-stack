package com.restaurantapp.demo.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private Double salary;
    private String position;
    private LocalDate dateJoined;
    private LocalDate dateLeft;
    private String cin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID userId;
}
