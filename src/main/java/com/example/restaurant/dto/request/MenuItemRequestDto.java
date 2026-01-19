package com.example.restaurant.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;


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
    private Long categoryId;

}
