package com.example.restaurant.mapper;


import com.example.restaurant.dto.request.RestaurantTableRequestDto;
import com.example.restaurant.dto.response.RestaurantTableResponseDto;
import com.example.restaurant.entity.RestaurantTable;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantTableMapper {
    RestaurantTable toTableEntity(RestaurantTableRequestDto tableDto);
    RestaurantTableResponseDto toTableDto(RestaurantTable table);
    List<RestaurantTableResponseDto> toTableDto(List<RestaurantTable> table);
}
