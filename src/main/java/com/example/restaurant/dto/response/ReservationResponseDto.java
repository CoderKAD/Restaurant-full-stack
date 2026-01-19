package com.example.restaurant.dto.response;


import com.example.restaurant.entity.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class ReservationResponseDto {
    private Long id;
    private Integer partySize;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate  startAt;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endAt;
    private ReservationStatus status;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;


}
