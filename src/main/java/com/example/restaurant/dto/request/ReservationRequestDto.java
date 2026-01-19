package com.example.restaurant.dto.request;

import com.example.restaurant.entity.enums.ReservationStatus;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDto {



    @NotNull(message = "Party size is required")
    @Min(value = 1, message = "Party size must be at least 1")
    private Integer partySize;

    @NotNull(message = "Start date is required")
    @Future(message = "Reservation start date must be in the future")
    private LocalDate startAt;

    @NotNull(message = "End date is required")
    private LocalDate endAt;

    private ReservationStatus status;

    @Size(max = 500, message = "Notes must be at most 500 characters")
    private String notes;

    @NotNull(message = "CreatedBy user ID is required")
    private UUID createdById;

    private UUID updatedById;


}
