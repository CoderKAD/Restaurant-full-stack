package com.restaurantapp.demo.controller;

import com.restaurantapp.demo.dto.ResponseDto.CategoryMenuResponseDto;
import com.restaurantapp.demo.dto.ResponseDto.MenuItemResponseDto;
import com.restaurantapp.demo.dto.requestDto.CategoryMenuRequestDto;
import com.restaurantapp.demo.dto.requestDto.MenuItemRequestDto;
import com.restaurantapp.demo.service.MenuManagementService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuManagementService menuManagementService;

    public MenuController(MenuManagementService menuManagementService) {
        this.menuManagementService = menuManagementService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryMenuResponseDto>> getAllCategories() {
        return ResponseEntity.ok(menuManagementService.getAllCategories());
    }



    @PostMapping("/categories")
    public ResponseEntity<CategoryMenuResponseDto> createCategory(
            @Valid @RequestBody CategoryMenuRequestDto dto
    ) {
        return ResponseEntity.ok(menuManagementService.createCategory(dto));
    }

    @PostMapping(value = "/categories", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryMenuResponseDto> createCategoryMultipart(
            @Valid @ModelAttribute CategoryMenuRequestDto dto
    ) {
        return ResponseEntity.ok(menuManagementService.createCategory(dto));
    }

    @PostMapping(value = "/categories", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<CategoryMenuResponseDto> createCategoryForm(
            @Valid @ModelAttribute CategoryMenuRequestDto dto
    ) {
        return ResponseEntity.ok(menuManagementService.createCategory(dto));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryMenuResponseDto> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryMenuRequestDto dto
    ) {
        return ResponseEntity.ok(menuManagementService.updateCategory(id, dto));
    }

    @PutMapping(value = "/categories/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryMenuResponseDto> updateCategoryMultipart(
            @PathVariable UUID id,
            @Valid @ModelAttribute CategoryMenuRequestDto dto
    ) {
        return ResponseEntity.ok(menuManagementService.updateCategory(id, dto));
    }

    @PutMapping(value = "/categories/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<CategoryMenuResponseDto> updateCategoryForm(
            @PathVariable UUID id,
            @Valid @ModelAttribute CategoryMenuRequestDto dto
    ) {
        return ResponseEntity.ok(menuManagementService.updateCategory(id, dto));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        menuManagementService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<MenuItemResponseDto>> getAllMenuItems() {
        return ResponseEntity.ok(menuManagementService.getAllMenuItems());
    }

    @PostMapping(value = "/items", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MenuItemResponseDto> createMenuItem(
            @Valid @ModelAttribute MenuItemRequestDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return ResponseEntity.ok(menuManagementService.createMenuItem(dto, image));
    }

    @PostMapping(value = "/items", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<MenuItemResponseDto> createMenuItemForm(
            @Valid @ModelAttribute MenuItemRequestDto dto
    ) throws IOException {
        return ResponseEntity.ok(menuManagementService.createMenuItem(dto, null));
    }

    @PostMapping(value = "/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItemResponseDto> createMenuItemJson(
            @Valid @RequestBody MenuItemRequestDto dto
    ) throws IOException {
        return ResponseEntity.ok(menuManagementService.createMenuItem(dto, null));
    }

    @PutMapping(value = "/items/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MenuItemResponseDto> updateMenuItem(
            @PathVariable UUID id,
            @Valid @ModelAttribute MenuItemRequestDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return ResponseEntity.ok(menuManagementService.updateMenuItem(id, dto, image));
    }

    @PutMapping(value = "/items/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<MenuItemResponseDto> updateMenuItemForm(
            @PathVariable UUID id,
            @Valid @ModelAttribute MenuItemRequestDto dto
    ) throws IOException {
        return ResponseEntity.ok(menuManagementService.updateMenuItem(id, dto, null));
    }

    @PutMapping(value = "/items/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItemResponseDto> updateMenuItemJson(
            @PathVariable UUID id,
            @Valid @RequestBody MenuItemRequestDto dto
    ) throws IOException {
        return ResponseEntity.ok(menuManagementService.updateMenuItem(id, dto, null));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable UUID id) {
        menuManagementService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}
