package com.restaurantapp.demo.service;

import com.restaurantapp.demo.dto.ResponseDto.CategoryMenuResponseDto;
import com.restaurantapp.demo.dto.ResponseDto.MenuItemResponseDto;
import com.restaurantapp.demo.dto.requestDto.CategoryMenuRequestDto;
import com.restaurantapp.demo.dto.requestDto.MenuItemRequestDto;
import com.restaurantapp.demo.entity.CategoryMenu;
import com.restaurantapp.demo.entity.MenuItem;
import com.restaurantapp.demo.mapper.CategoryMenuMapper;
import com.restaurantapp.demo.mapper.MenuItemMapper;
import com.restaurantapp.demo.repository.CategoryMenuRepository;
import com.restaurantapp.demo.repository.MenuItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class MenuManagementService {

    private static final long MAX_FILE_SIZE_BYTES = 2L * 1024 * 1024;
    private static final List<String> ALLOWED_MIME_TYPES = List.of("image/jpeg", "image/png", "image/jpg");

    private final MenuItemRepository menuItemRepository;
    private final CategoryMenuRepository categoryMenuRepository;
    private final MenuItemMapper menuItemMapper;
    private final CategoryMenuMapper categoryMenuMapper;
    private final Path imageStorageRoot = Paths.get("uploads", "menu-items");
    private final Tika tika = new Tika();

    public MenuManagementService(MenuItemRepository menuItemRepository,
                                 CategoryMenuRepository categoryMenuRepository,
                                 MenuItemMapper menuItemMapper,
                                 CategoryMenuMapper categoryMenuMapper) {
        this.menuItemRepository = menuItemRepository;
        this.categoryMenuRepository = categoryMenuRepository;
        this.menuItemMapper = menuItemMapper;
        this.categoryMenuMapper = categoryMenuMapper;
    }

    public List<CategoryMenuResponseDto> getAllCategories() {
        return categoryMenuMapper.toDto(categoryMenuRepository.findAll());
    }

    public CategoryMenuResponseDto createCategory(CategoryMenuRequestDto dto) {
        if (categoryMenuRepository.existsByCategoryNameIgnoreCase(dto.getCategoryName())) {
            throw new IllegalArgumentException("Category name already exists: " + dto.getCategoryName());
        }
        if (categoryMenuRepository.existsBySortOrder(dto.getSortOrder())) {
            CategoryMenu lastSort = categoryMenuRepository.findTopByOrderBySortOrderDesc();
            throw new IllegalArgumentException(
                    "Sort order already exists: " + dto.getSortOrder() +
                            ". Last sort order is: " + (lastSort != null ? lastSort.getSortOrder() : "none")
            );
        }
        CategoryMenu entity = categoryMenuMapper.toEntity(dto);
        CategoryMenu saved = categoryMenuRepository.save(entity);
        return categoryMenuMapper.toDto(saved);
    }

    public CategoryMenuResponseDto updateCategory(UUID id, CategoryMenuRequestDto dto) {
        if (categoryMenuRepository.existsByCategoryNameIgnoreCaseAndIdNot(dto.getCategoryName(), id)) {
            throw new IllegalArgumentException("Category name already exists: " + dto.getCategoryName());
        }
        if (categoryMenuRepository.existsBySortOrderAndIdNot(dto.getSortOrder(), id)) {
            throw new IllegalArgumentException("Sort order already exists: " + dto.getSortOrder());
        }
        CategoryMenu existing = getCategoryEntity(id);
        categoryMenuMapper.updateEntity(dto, existing);
        return categoryMenuMapper.toDto(categoryMenuRepository.save(existing));
    }

    public void deleteCategory(UUID id) {
        if (!categoryMenuRepository.existsById(id)) {
            throw new EntityNotFoundException("CategoryMenu not found with id: " + id);
        }
        categoryMenuRepository.deleteById(id);
    }

    public List<MenuItemResponseDto> getAllMenuItems() {
        return menuItemMapper.toDto(menuItemRepository.findAll());
    }

    public MenuItemResponseDto createMenuItem(MenuItemRequestDto dto, MultipartFile image) throws IOException {
        MenuItem entity = menuItemMapper.toEntity(dto);
        entity.setId(null);
        entity.setCategory(getCategoryEntity(dto.getCategoryId()));
        entity.setImageUrl(storeImage(image));
        return menuItemMapper.toDto(menuItemRepository.save(entity));
    }

    public MenuItemResponseDto updateMenuItem(UUID id, MenuItemRequestDto dto, MultipartFile image) throws IOException {
        MenuItem existing = getMenuItemEntity(id);
        String oldImageUrl = existing.getImageUrl();
        menuItemMapper.updateEntity(dto, existing);
        existing.setCategory(getCategoryEntity(dto.getCategoryId()));

        String storedPath = storeImage(image);
        if (storedPath != null) {
            deleteImageIfExists(oldImageUrl);
            existing.setImageUrl(storedPath);
        }

        return menuItemMapper.toDto(menuItemRepository.save(existing));
    }

    public void deleteMenuItem(UUID id) {
        MenuItem existing = getMenuItemEntity(id);
        try {
            deleteImageIfExists(existing.getImageUrl());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to delete image for menu item: " + id, e);
        }
        menuItemRepository.delete(existing);
    }

    private CategoryMenu getCategoryEntity(UUID id) {
        return categoryMenuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CategoryMenu not found with id: " + id));
    }

    private MenuItem getMenuItemEntity(UUID id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MenuItem not found with id: " + id));
    }

    private String storeImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        validateImage(file);
        Files.createDirectories(imageStorageRoot);

        String original = file.getOriginalFilename();
        String extension = (original != null && original.contains(".")) ? original.substring(original.lastIndexOf('.')) : "";
        String fileName = UUID.randomUUID() + extension;

        Path target = imageStorageRoot.resolve(fileName);
        Files.copy(file.getInputStream(), target);

        return target.toString();
    }

    private void validateImage(MultipartFile file) throws IOException {
        String mimeType = tika.detect(file.getInputStream(), file.getOriginalFilename());

        if (!ALLOWED_MIME_TYPES.contains(mimeType)) {
            throw new IllegalArgumentException("Invalid file type. Allowed types: " + ALLOWED_MIME_TYPES);
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new IllegalArgumentException("File is too large. Maximum allowed size: " + MAX_FILE_SIZE_BYTES + " bytes.");
        }
    }

    private void deleteImageIfExists(String imageUrl) throws IOException {
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }
        Path oldPath = Paths.get(imageUrl);
        if (Files.exists(oldPath)) {
            Files.deleteIfExists(oldPath);
        }
    }
}
