package com.restaurantapp.demo.dto.ResponseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurantapp.demo.entity.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {
    private Long id;
    private Integer partySize;
    private LocalDate startAt;
    private LocalDate endAt;
    private ReservationStatus status;
    private String notes;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime updatedAt;
    private UUID createdById;
    private UUID updatedById;
    private List<Long> tableIds;
}
