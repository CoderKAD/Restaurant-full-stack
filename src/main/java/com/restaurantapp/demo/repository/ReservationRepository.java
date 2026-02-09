package com.restaurantapp.demo.repository;

import com.restaurantapp.demo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
