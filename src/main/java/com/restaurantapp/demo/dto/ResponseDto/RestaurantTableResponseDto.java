package com.restaurantapp.demo.dto.ResponseDto;

import com.restaurantapp.demo.entity.enums.TableStatus;
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
public class RestaurantTableResponseDto {
    private Long id;
    private String label;
    private Integer seats;
    private String code;
    private Boolean active;
    private TableStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID userId;
}
