package com.restaurantapp.demo.repository;

import com.restaurantapp.demo.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {
    boolean existsByCinIgnoreCase(String cin);

    boolean existsByCinIgnoreCaseAndIdNot(String cin, UUID id);
}
