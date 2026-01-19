package com.example.restaurant.repository;

import com.example.restaurant.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustmorRepository extends JpaRepository<Customer , UUID> {
}
