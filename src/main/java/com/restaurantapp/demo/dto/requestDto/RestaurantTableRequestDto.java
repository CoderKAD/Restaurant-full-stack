package com.restaurantapp.demo.dto.requestDto;

import com.restaurantapp.demo.entity.enums.TableStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotBlank(message = "Label is required")
    @Size(min = 2, max = 50, message = "Label must be between 2 and 50 characters")
    private String label;

    @NotNull(message = "Seats is required")
    @Positive(message = "Seats must be greater than 0")
    private Integer seats;

    @NotNull(message = "Active is required")
    private Boolean active;

    @NotNull(message = "Status is required")
    private TableStatus status;

    private UUID userId;
}
