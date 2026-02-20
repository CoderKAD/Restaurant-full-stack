package com.restaurantapp.demo.repository;

import com.restaurantapp.demo.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    boolean existsByPublicCode(String publicCode);
}
