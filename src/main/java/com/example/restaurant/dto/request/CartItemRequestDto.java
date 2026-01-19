package com.example.restaurant.dto.request;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequestDto {

    @Min(value = 1)
    private Integer quantity = 1;
    @NotNull
    private UUID customerId;

    @NotNull
    private Long menuItemId;


}
