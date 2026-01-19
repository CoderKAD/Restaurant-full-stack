package com.example.restaurant.dto.response;

import com.example.restaurant.entity.enums.TableStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class RestaurantTableResponseDto {
    private Long id;
    private String label;
    private Integer seats;
    private String code;
    private Boolean active;
    private TableStatus status;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    private UUID userId;


    private List<OrderResponseDto> orders;

    private List<ReservationResponseDto> reservations;
}
