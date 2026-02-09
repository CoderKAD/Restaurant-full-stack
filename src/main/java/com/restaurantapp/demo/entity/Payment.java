package com.restaurantapp.demo.entity;

import com.restaurantapp.demo.entity.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be greater than 0")
    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @CreatedDate
    @Column( name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column( name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Many-to-One: User who processed this payment
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // One-to-One: Order this payment is for
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
