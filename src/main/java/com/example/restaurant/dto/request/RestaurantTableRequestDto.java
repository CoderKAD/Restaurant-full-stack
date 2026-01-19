package com.example.restaurant.dto.request;

import com.example.restaurant.entity.enums.TableStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTableRequestDto {
    @NotBlank(message = "Label is required")
    @Size(min = 2, max = 50, message = "Label must be between 2 and 50 characters")
    private String label;

    @NotNull(message = "Seats is required")
    private Integer seats;

    private String code;

    private Boolean active;

    private TableStatus status;
    private UUID userId;

}
