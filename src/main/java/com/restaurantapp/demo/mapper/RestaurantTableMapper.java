package com.restaurantapp.demo.mapper;

import com.restaurantapp.demo.dto.ResponseDto.RestaurantTableResponseDto;
import com.restaurantapp.demo.dto.requestDto.RestaurantTableRequestDto;
import com.restaurantapp.demo.entity.RestaurantTable;
import com.restaurantapp.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestaurantTableMapper {
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserId")
    RestaurantTable toEntity(RestaurantTableRequestDto dto);

    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserId")
    void updateEntity(RestaurantTableRequestDto dto, @MappingTarget RestaurantTable entity);

    @Mapping(target = "userId", source = "user.id")
    RestaurantTableResponseDto toDto(RestaurantTable entity);

    @Mapping(target = "userId", source = "user.id")
    List<RestaurantTableResponseDto> toDto(List<RestaurantTable> entity);

    @Named("mapUserId")
    default User mapUserId(UUID userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }
}
