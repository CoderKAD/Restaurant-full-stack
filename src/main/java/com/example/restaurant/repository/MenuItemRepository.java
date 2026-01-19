package com.example.restaurant.repository;

import com.example.restaurant.entity.MenuCategory;
import com.example.restaurant.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {

}
