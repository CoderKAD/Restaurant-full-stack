package com.example.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean active;
    private String imageUrl;
    private String prepStation;
    private MenuCategoryResponseDto category;
    private List<OrderItemResponseDto> orderItems;
    private List<CartItemResponseDto> cartItems;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
