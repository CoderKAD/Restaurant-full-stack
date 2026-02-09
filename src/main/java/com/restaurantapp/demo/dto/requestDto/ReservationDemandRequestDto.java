package com.restaurantapp.demo.dto.requestDto;

import com.restaurantapp.demo.entity.enums.DemandStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDemandRequestDto {
    @NotNull
    private DemandStatus status;
    @NotNull
    private Long reservationId;
    @NotNull
    private UUID customerId;
}
