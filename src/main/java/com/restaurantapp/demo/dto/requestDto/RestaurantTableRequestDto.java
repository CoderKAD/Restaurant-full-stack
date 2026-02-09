package com.restaurantapp.demo.dto.requestDto;

import com.restaurantapp.demo.entity.enums.TableStatus;
import jakarta.validation.constraints.NotBlank;
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
public class RestaurantTableRequestDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String label;
    private Integer seats;
    private String code;
    private Boolean active;
    private TableStatus status;
    private UUID userId;
}
