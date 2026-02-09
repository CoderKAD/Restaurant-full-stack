package com.restaurantapp.demo.dto.requestDto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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

public class MenuItemRequestDto {
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;
    @Size(max = 500)
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    private Boolean active;
    private String imageUrl;
    @Size(max = 50)
    private String prepStation;
    @NotNull
    private UUID categoryId;
}
