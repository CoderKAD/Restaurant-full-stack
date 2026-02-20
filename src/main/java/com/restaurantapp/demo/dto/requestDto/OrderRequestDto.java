package com.restaurantapp.demo.dto.requestDto;

import com.restaurantapp.demo.entity.enums.OrderStatus;
import com.restaurantapp.demo.entity.enums.OrderType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotNull
    private OrderType typeOrder;
    @NotNull
    private OrderStatus status;
    @Size(max = 500)
    private String notes;
    private Long restaurantTableId;
    private UUID createdById;
    private UUID updatedById;
}
