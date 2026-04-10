package com.restaurantapp.demo.dto.requestDto;

import com.restaurantapp.demo.entity.enums.ReservationStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDto {
    @NotNull(message = "Party size is required")
    @Positive(message = "Party size must be greater than 0")
    private Integer partySize;

    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    private LocalDate startAt;

    private LocalDate endAt;

    @NotNull(message = "Reservation status is required")
    private ReservationStatus status;

    @Size(max = 500, message = "Notes must be at most 500 characters")
    private String notes;

    private UUID createdById;
    private UUID updatedById;

    @NotEmpty(message = "At least one table id is required")
    private List<Long> tableIds;

    @AssertTrue(message = "End date must be on or after start date")
    public boolean isEndAtValid() {
        return endAt == null || startAt == null || !endAt.isBefore(startAt);
    }
}
