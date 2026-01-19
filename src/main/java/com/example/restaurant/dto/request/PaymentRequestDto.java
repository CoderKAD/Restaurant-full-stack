package com.example.restaurant.dto.request;
import com.example.restaurant.entity.enums.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Order ID is required")
    private UUID orderId;

}
