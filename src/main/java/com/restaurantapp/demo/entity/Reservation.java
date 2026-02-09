package com.restaurantapp.demo.entity;


import com.restaurantapp.demo.entity.enums.ReservationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "party_size")
    private Integer partySize;

    @Column(name = "start_at")
    @Future(message = "Reservation date must be in the future")
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Size(max = 500, message = "Notes must be at most 500 characters")
    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column( name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column( name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Many-to-One: User who created this reservation
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    // Many-to-One: User who last updated this reservation
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    // Many-to-Many: Tables reserved
    @ManyToMany
    @JoinTable(
            name = "reservation_tables",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "table_id")
    )
    private List<RestaurantTable> tables;

    // One-to-Many: Demands/requests for this reservation (ADDED - CORRECTED)
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReservationDemand> demands;
}
