package com.restaurantapp.demo.mapper;

import com.restaurantapp.demo.dto.ResponseDto.OrderResponseDto;
import com.restaurantapp.demo.dto.requestDto.OrderRequestDto;
import com.restaurantapp.demo.entity.Order;
import com.restaurantapp.demo.entity.RestaurantTable;
import com.restaurantapp.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "restaurantTable", source = "restaurantTableId", qualifiedByName = "mapRestaurantTableId")
    @Mapping(target = "createdBy", source = "createdById", qualifiedByName = "mapUserId")
    @Mapping(target = "updatedBy", source = "updatedById", qualifiedByName = "mapUserId")
    Order toEntity(OrderRequestDto dto);

    @Mapping(target = "restaurantTable", source = "restaurantTableId", qualifiedByName = "mapRestaurantTableId")
    @Mapping(target = "createdBy", source = "createdById", qualifiedByName = "mapUserId")
    @Mapping(target = "updatedBy", source = "updatedById", qualifiedByName = "mapUserId")
    void updateEntity(OrderRequestDto dto, @MappingTarget Order entity);

    @Mapping(target = "restaurantTableId", source = "restaurantTable.id")
    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "updatedById", source = "updatedBy.id")
    OrderResponseDto toDto(Order entity);

    @Mapping(target = "restaurantTableId", source = "restaurantTable.id")
    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "updatedById", source = "updatedBy.id")
    List<OrderResponseDto> toDto(List<Order> entity);

    @Named("mapRestaurantTableId")
    default RestaurantTable mapRestaurantTableId(Long restaurantTableId) {
        if (restaurantTableId == null) {
            return null;
        }
        RestaurantTable table = new RestaurantTable();
        table.setId(restaurantTableId);
        return table;
    }

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
