package com.restaurantapp.demo.dto.requestDto;

import jakarta.validation.constraints.Min;
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
public class OrderItemRequestDto {
    @NotNull
    @Min(1)
    private Integer quantity;
    @Size(max = 500)
    private String notes;
    @NotNull
    private UUID orderId;
    @NotNull
    private UUID menuItemId;
}
