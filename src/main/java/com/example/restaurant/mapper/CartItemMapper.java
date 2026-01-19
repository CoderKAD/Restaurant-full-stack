package com.example.restaurant.mapper;

import com.example.restaurant.dto.request.CartItemRequestDto;
import com.example.restaurant.dto.response.CartItemResponseDto;
import com.example.restaurant.entity.CartItem;
import org.mapstruct.*;

import java.util.List;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface CartItemMapper {
    CartItem toCartEntity(CartItemRequestDto cartDto);
    CartItemResponseDto toCartDto(CartItem cartItem);
    List<CartItemResponseDto> toCartDto(List<CartItem> cartItem);

}
