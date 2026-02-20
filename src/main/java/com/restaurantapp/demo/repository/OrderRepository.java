package com.restaurantapp.demo.repository;

import com.restaurantapp.demo.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    boolean existsByPublicCode(String publicCode);

    @EntityGraph(attributePaths = {"orderItems"})
    List<Order> findAllBy();

    @EntityGraph(attributePaths = {"orderItems"})
    Optional<Order> findById(UUID id);
}
