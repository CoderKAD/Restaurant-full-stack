package com.example.restaurant.repository;

import com.example.restaurant.entity.ReservationDemand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDemandRepository extends JpaRepository<ReservationDemand ,Long> {
}
