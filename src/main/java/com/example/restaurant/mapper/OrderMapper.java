package com.example.restaurant.mapper;

import com.example.restaurant.dto.request.OrderRequestDto;
import com.example.restaurant.dto.response.OrderResponseDto;
import com.example.restaurant.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface OrderMapper {
    Order toOrderEntity(OrderRequestDto orderDto);
    OrderResponseDto toOrderDto(Order order);
    List<OrderResponseDto> toOrderDto(List<Order> order);

}
