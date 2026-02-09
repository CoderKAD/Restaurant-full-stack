package com.restaurantapp.demo.dto.ResponseDto;

import com.restaurantapp.demo.entity.enums.DemandStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDemandResponseDto {
    private Long id;
    private DemandStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long reservationId;
    private UUID customerId;
}
