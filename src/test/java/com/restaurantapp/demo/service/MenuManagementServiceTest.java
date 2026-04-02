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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuManagementServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private CategoryMenuRepository categoryMenuRepository;
    @Mock
    private MenuItemMapper menuItemMapper;
    @Mock
    private CategoryMenuMapper categoryMenuMapper;

    private MenuManagementService menuManagementService;

    @BeforeEach
    void setUp() {
        menuManagementService = new MenuManagementService(
                menuItemRepository,
                categoryMenuRepository,
                menuItemMapper,
                categoryMenuMapper
        );
    }

    @Test
    void createCategory_shouldThrow_whenCategoryNameExists() {
        CategoryMenuRequestDto dto = new CategoryMenuRequestDto("Drinks", 1, true);

        when(categoryMenuRepository.existsByCategoryNameIgnoreCase("Drinks")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> menuManagementService.createCategory(dto)
        );

        assertEquals("Category name already exists: Drinks", ex.getMessage());
        verify(categoryMenuRepository, never()).save(any());
    }

    @Test
    void createCategory_shouldThrow_whenSortOrderExists() {
        CategoryMenuRequestDto dto = new CategoryMenuRequestDto("Lunch", 1, true);
        CategoryMenu lastSort = new CategoryMenu();
        lastSort.setSortOrder(5);

        when(categoryMenuRepository.existsByCategoryNameIgnoreCase("Lunch")).thenReturn(false);
        when(categoryMenuRepository.existsBySortOrder(1)).thenReturn(true);
        when(categoryMenuRepository.findTopByOrderBySortOrderDesc()).thenReturn(lastSort);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> menuManagementService.createCategory(dto)
        );

        assertEquals("Sort order already exists: 1. Last sort order is: 5", ex.getMessage());
        verify(categoryMenuRepository, never()).save(any());
    }

    @Test
    void createCategory_shouldReturnDto_whenValid() {
        CategoryMenuRequestDto dto = new CategoryMenuRequestDto("Dinner", 2, true);
        CategoryMenu entity = new CategoryMenu();
        CategoryMenu saved = new CategoryMenu();
        CategoryMenuResponseDto responseDto = new CategoryMenuResponseDto();

        when(categoryMenuRepository.existsByCategoryNameIgnoreCase("Dinner")).thenReturn(false);
        when(categoryMenuRepository.existsBySortOrder(2)).thenReturn(false);
        when(categoryMenuMapper.toEntity(dto)).thenReturn(entity);
        when(categoryMenuRepository.save(entity)).thenReturn(saved);
        when(categoryMenuMapper.toDto(saved)).thenReturn(responseDto);

        CategoryMenuResponseDto actual = menuManagementService.createCategory(dto);

        assertSame(responseDto, actual);
        verify(categoryMenuMapper).toEntity(dto);
        verify(categoryMenuRepository).save(entity);
        verify(categoryMenuMapper).toDto(saved);
    }

    @Test
    void deleteCategory_shouldThrow_whenMissing() {
        UUID id = UUID.randomUUID();
        when(categoryMenuRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> menuManagementService.deleteCategory(id));
        verify(categoryMenuRepository, never()).deleteById(any());
    }

    @Test
    void createMenuItem_shouldSave_whenImageMissing() throws Exception {
        UUID categoryId = UUID.randomUUID();
        MenuItemRequestDto dto = new MenuItemRequestDto("Latte", "Coffee", 4.5, true, null, "Bar", categoryId);
        MenuItem entity = new MenuItem();
        MenuItemResponseDto responseDto = new MenuItemResponseDto();
        CategoryMenu category = new CategoryMenu();

        when(menuItemMapper.toEntity(dto)).thenReturn(entity);
        when(categoryMenuRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(menuItemRepository.save(entity)).thenReturn(entity);
        when(menuItemMapper.toDto(entity)).thenReturn(responseDto);

        MenuItemResponseDto actual = menuManagementService.createMenuItem(dto, null);

        assertSame(responseDto, actual);
        verify(menuItemMapper).toEntity(dto);
        verify(menuItemRepository).save(entity);
        verify(menuItemMapper).toDto(entity);
        assertEquals(category, entity.getCategory());
    }

    @Test
    void deleteMenuItem_shouldRemove_whenExists() {
        UUID id = UUID.randomUUID();
        MenuItem menuItem = new MenuItem();

        when(menuItemRepository.findById(id)).thenReturn(Optional.of(menuItem));

        menuManagementService.deleteMenuItem(id);

        verify(menuItemRepository).delete(menuItem);
    }
}
