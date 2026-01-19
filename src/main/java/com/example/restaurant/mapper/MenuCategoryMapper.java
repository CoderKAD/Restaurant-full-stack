package com.example.restaurant.mapper;

import com.example.restaurant.dto.request.MenuCategoryRequestDto;
import com.example.restaurant.dto.response.MenuCategoryResponseDto;
import com.example.restaurant.entity.MenuCategory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuCategoryMapper {

    MenuCategory toEntity(MenuCategoryRequestDto menuCategoryDto);
    MenuCategoryResponseDto toDto(MenuCategory menuCategories);
    List<MenuCategoryResponseDto> toDto(List<MenuCategory> menuCategories);
}
