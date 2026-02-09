package com.restaurantapp.demo.repository;

import com.restaurantapp.demo.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuItemRepository  extends JpaRepository<MenuItem , UUID> {

}
