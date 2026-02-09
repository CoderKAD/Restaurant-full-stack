package com.restaurantapp.demo.dto.requestDto;

import com.restaurantapp.demo.entity.enums.ReservationStatus;
import jakarta.validation.constraints.Future;
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
    private Integer partySize;
    @Future
    private LocalDate startAt;
    private LocalDate endAt;
    private ReservationStatus status;
    @Size(max = 500)
    private String notes;
    private UUID createdById;
    private UUID updatedById;
    private List<Long> tableIds;
}
