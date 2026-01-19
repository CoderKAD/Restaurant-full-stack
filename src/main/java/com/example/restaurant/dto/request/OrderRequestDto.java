package com.example.restaurant.dto.request;

import com.example.restaurant.entity.enums.OrderStatus;
import com.example.restaurant.entity.enums.OrderType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotNull(message = "Table ID is required")
    private Long restaurantTableId;

    @NotNull(message = "Order type is required")
    private OrderType typeOrder;

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    @Size(max = 1000, message = "Notes must be at most 1000 characters")
    private String notes;

    @NotNull(message = "CreatedBy User ID is required")
    private UUID createdById;

    private UUID updatedById;
}
