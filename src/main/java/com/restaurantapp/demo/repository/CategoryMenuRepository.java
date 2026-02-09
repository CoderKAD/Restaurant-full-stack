package com.restaurantapp.demo.repository;

import com.restaurantapp.demo.entity.CategoryMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryMenuRepository extends JpaRepository<CategoryMenu, UUID> {
    boolean existsBySortOrder(Integer sortOrder);
    boolean existsBySortOrderAndIdNot(Integer sortOrder, UUID id);
    CategoryMenu findTopByOrderBySortOrderDesc();
    boolean existsByCategoryNameIgnoreCase(String categoryName);
    boolean existsByCategoryNameIgnoreCaseAndIdNot(String categoryName, UUID id);
}
