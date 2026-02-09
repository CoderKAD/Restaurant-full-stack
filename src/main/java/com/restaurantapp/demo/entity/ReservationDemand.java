package com.restaurantapp.demo.entity;



import com.restaurantapp.demo.entity.enums.DemandStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class ReservationDemand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DemandStatus status;

    @CreatedDate
    @Column( name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column( name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Many-to-One: Reservation this demand is for (CHANGED from @OneToOne to @ManyToOne)
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    // Many-to-One: Customer who made this demand
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
