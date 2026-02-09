package com.restaurantapp.demo.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1)
    private Integer quantity = 1;

    @CreatedDate
    @Column( name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column( name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Many-to-One: Customer who owns this cart item
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Many-to-One: Menu item in this cart
    @ManyToOne
    @JoinColumn(name = "menuItem_id")
    private MenuItem menuItem;
}
