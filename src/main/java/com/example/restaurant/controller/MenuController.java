package com.example.restaurant.controller;

import com.example.restaurant.dto.request.MenuCategoryRequestDto;
import com.example.restaurant.dto.request.MenuItemRequestDto;
import com.example.restaurant.dto.response.MenuCategoryResponseDto;
import com.example.restaurant.dto.response.MenuItemResponseDto;
import com.example.restaurant.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;
    public MenuController(MenuService menuService){
        this.menuService=menuService;

    }

    @PostMapping("/categories")
    public ResponseEntity<MenuCategoryResponseDto> createCategory(@Validated @RequestBody MenuCategoryRequestDto request) {
        return ResponseEntity.ok(menuService.createCategory(request));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<MenuCategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(menuService.getAllCategories());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<MenuCategoryResponseDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getCategoryById(id));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<MenuCategoryResponseDto> updateCategory(@PathVariable Long id,
                                                               @Validated @RequestBody MenuCategoryRequestDto request) {
        return ResponseEntity.ok(menuService.updateCategory(id, request));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        menuService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }


    @PostMapping("/items")
    public ResponseEntity<MenuItemResponseDto> createMenuItem(@Validated @RequestBody MenuItemRequestDto request) {
        return ResponseEntity.ok(menuService.createMenuItem(request));
    }

    @GetMapping("/items")
    public ResponseEntity<List<MenuItemResponseDto>> getAllMenuItems() {
        return ResponseEntity.ok(menuService.getAllMenuItems());
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<MenuItemResponseDto> getMenuItemById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuItemById(id));
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<MenuItemResponseDto> updateMenuItem(@PathVariable Long id,
                                                           @Validated @RequestBody MenuItemRequestDto request) {
        return ResponseEntity.ok(menuService.updateMenuItem(id, request));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity.ok("Menu item deleted successfully");
    }



}
