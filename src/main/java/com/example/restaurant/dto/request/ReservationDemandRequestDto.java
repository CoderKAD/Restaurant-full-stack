package com.example.restaurant.dto.request;

import com.example.restaurant.entity.enums.DemandStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDemandRequestDto {
    @NotNull(message = "Status is required")
    private DemandStatus status;

    @NotNull(message = "Reservation ID is required")
    private Long reservationId;

    @NotNull(message = "Customer ID is required")
    private UUID customerId;
}
