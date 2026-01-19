package com.example.restaurant.entity;
import com.example.restaurant.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_name")
    private String username;

    //private String password;
    @Column(name = "password_hash")
    private String passwordHash;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role roles;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Customer> customers;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Staff> staffs;

    @OneToMany(mappedBy = "createdBy" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> ordersCreated;

    @OneToMany(mappedBy = "updatedBy" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> ordersUpdated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RestaurantTable> restaurantTables;



    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservationsCreated;

    @OneToMany(mappedBy = "updatedBy" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservationsUpdated;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;
}
