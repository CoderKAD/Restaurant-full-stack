package com.example.restaurant.service;

import com.example.restaurant.dto.request.MenuCategoryRequestDto;
import com.example.restaurant.dto.request.MenuItemRequestDto;
import com.example.restaurant.dto.response.MenuCategoryResponseDto;
import com.example.restaurant.dto.response.MenuItemResponseDto;
import com.example.restaurant.entity.MenuCategory;
import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.mapper.MenuCategoryMapper;
import com.example.restaurant.mapper.MenuItemMapper;
import com.example.restaurant.repository.MenuCategoryRepository;
import com.example.restaurant.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuItemRepository nemuRepository;
    private final MenuItemMapper menuMapper;
    private final MenuCategoryMapper categoryMapper;
    private final MenuCategoryRepository categoryRepository;
    //constructeur
    public MenuService(
                       MenuItemRepository nemuRepository ,
                       MenuItemMapper menuMapper,
                       MenuCategoryMapper categoryMapper ,
                       MenuCategoryRepository categoryRepository){
        this.nemuRepository=nemuRepository;
        this.menuMapper=menuMapper;
        this.categoryMapper=categoryMapper;
        this.categoryRepository=categoryRepository;

    }

    public MenuCategoryResponseDto createCategory(MenuCategoryRequestDto request) {
        MenuCategory entity = categoryMapper.toEntity(request);
        return categoryMapper.(categoryRepository.save(entity));
    }

    public List<MenuCategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public MenuCategoryResponseDto getCategoryById(Long id) {
        MenuCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toDto(category);
    }

    public MenuCategoryResponseDto updateCategory(Long id, MenuCategoryRequestDto request) {
        MenuCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        category.setCategoryName(request.getCategoryName());
        category.setSortOrder(request.getSortOrder());
        category.setActive(request.getActive());

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        MenuCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }



    public MenuItemResponseDto createMenuItem(MenuItemRequestDto request) {
        MenuCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        MenuItem menuItem = menuMapper.toEntity(request);
        menuItem.setCategory(category);

        return menuMapper.toDto(nemuRepository.save(menuItem));
    }

    public List<MenuItemResponseDto> getAllMenuItems() {
        return nemuRepository.findAll()
                .stream()
                .map(menuMapper::toDto)
                .collect(Collectors.toList());
    }

    public MenuItemResponseDto getMenuItemById(Long id) {
        MenuItem menuItem = nemuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        return menuMapper.toDto(menuItem);
    }

    public MenuItemResponseDto updateMenuItem(Long id, MenuItemRequestDto request) {
        MenuItem menuItem = nemuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));

        MenuCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setActive(request.getActive());
        menuItem.setImageUrl(request.getImageUrl());
        menuItem.setPrepStation(request.getPrepStation());
        menuItem.setCategory(category);

        return menuMapper.toDto(nemuRepository.save(menuItem));
    }

    public void deleteMenuItem(Long id) {
        MenuItem menuItem = nemuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        nemuRepository.delete(menuItem);
    }





}
