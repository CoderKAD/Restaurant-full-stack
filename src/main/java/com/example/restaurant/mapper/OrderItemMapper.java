package com.example.restaurant.mapper;

import com.example.restaurant.dto.request.OrderItemRequestDto;
import com.example.restaurant.dto.response.CustomerResponseDto;
import com.example.restaurant.dto.response.OrderItemResponseDto;
import com.example.restaurant.entity.Customer;
import com.example.restaurant.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {
    OrderItem toOrderItemEntity(OrderItemRequestDto orderItemDto);
    OrderItemResponseDto toOrderItemDto(OrderItem orderitem);
    List<OrderItemResponseDto> toOrderItemDto(List<OrderItem> orderItems);
}
