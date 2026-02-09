package com.restaurantapp.demo.mapper;

import com.restaurantapp.demo.dto.ResponseDto.MenuItemResponseDto;
import com.restaurantapp.demo.dto.requestDto.MenuItemRequestDto;
import com.restaurantapp.demo.entity.CategoryMenu;
import com.restaurantapp.demo.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategoryId")
    MenuItem toEntity(MenuItemRequestDto dto);

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategoryId")
    void updateEntity(MenuItemRequestDto dto, @MappingTarget MenuItem entity);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.categoryName")
    MenuItemResponseDto toDto(MenuItem entity);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.categoryName")
    List<MenuItemResponseDto> toDto(List<MenuItem> entity);

    @Named("mapCategoryId")
    default CategoryMenu mapCategoryId(UUID categoryId) {
        if (categoryId == null) {
            return null;
        }
        CategoryMenu category = new CategoryMenu();
        category.setId(categoryId);
        return category;
    }
}
