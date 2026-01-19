package com.example.restaurant.dto.request;
import jakarta.validation.constraints.*;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryRequestDto {

    @Size(max = 100)
    String categoryName;

    @Min(1)
    Integer sortOrder;

    Boolean active;

}
