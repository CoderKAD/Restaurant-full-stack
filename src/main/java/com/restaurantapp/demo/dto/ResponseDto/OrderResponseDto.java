package com.restaurantapp.demo.dto.ResponseDto;

import com.restaurantapp.demo.entity.enums.OrderStatus;
import com.restaurantapp.demo.entity.enums.OrderType;
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
public class OrderResponseDto {
    private UUID id;
    private String publicCode;
    private OrderType typeOrder;
    private OrderStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long restaurantTableId;
    private UUID createdById;
    private UUID updatedById;
}
