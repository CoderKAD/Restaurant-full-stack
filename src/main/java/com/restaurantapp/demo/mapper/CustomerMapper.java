package com.restaurantapp.demo.mapper;

import com.restaurantapp.demo.dto.ResponseDto.CustomerResponseDto;
import com.restaurantapp.demo.dto.requestDto.CustomerRequestDto;
import com.restaurantapp.demo.entity.Customer;
import com.restaurantapp.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserId")
    Customer toEntity(CustomerRequestDto dto);

    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserId")
    void updateEntity(CustomerRequestDto dto, @MappingTarget Customer entity);

    @Mapping(target = "userId", source = "user.id")
    CustomerResponseDto toDto(Customer entity);

    @Mapping(target = "userId", source = "user.id")
    List<CustomerResponseDto> toDto(List<Customer> entity);

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
