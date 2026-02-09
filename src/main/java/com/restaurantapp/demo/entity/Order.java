package com.restaurantapp.demo.entity;


import com.restaurantapp.demo.entity.enums.OrderStatus;
import com.restaurantapp.demo.entity.enums.OrderType;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "public_code")
    private String publicCode;

    @Enumerated(EnumType.STRING)
    private OrderType typeOrder;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column( name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column( name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Many-to-One: Table where this order is placed
    @ManyToOne
    @JoinColumn(name = "table_id")
    private RestaurantTable restaurantTable;

    // Many-to-One: User who created this order
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    // Many-to-One: User who last updated this order
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    // One-to-Many: Items in this order
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    // One-to-One: Payment for this order
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
}
