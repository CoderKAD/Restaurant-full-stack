package com.restaurantapp.demo.repository;

import com.restaurantapp.demo.entity.ReservationDemand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDemandRepository extends JpaRepository<ReservationDemand, Long> {
}
