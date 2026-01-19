package com.example.restaurant.dto.response;


import com.example.restaurant.entity.enums.OrderStatus;
import com.example.restaurant.entity.enums.OrderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private UUID id;
    private String publicCode;
    private OrderType typeOrder;
    private OrderStatus status;
    private String notes;
    private Long tableId;
    private UUID createdBy;
    private UUID updatedBy;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

}
