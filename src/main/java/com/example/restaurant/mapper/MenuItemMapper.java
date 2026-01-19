package com.example.restaurant.mapper;


import com.example.restaurant.dto.request.MenuItemRequestDto;
import com.example.restaurant.dto.response.MenuItemResponseDto;
import com.example.restaurant.entity.MenuItem;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;



@Mapper(componentModel = "spring", uses = {MenuCategoryMapper.class})
public interface MenuItemMapper {

    MenuItemMapper INSTANCE = Mappers.getMapper(MenuItemMapper.class);

    @Mapping(source = "categoryId", target = "category.id")
    MenuItem toEntity(MenuItemRequestDto dto);

    @Mapping(source = "category.id", target = "categoryId")
    MenuItemResponseDto toDto(MenuItem entity);
}
