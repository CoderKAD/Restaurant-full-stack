package com.restaurantapp.demo.mapper;

import com.restaurantapp.demo.dto.ResponseDto.UserResponseDto;
import com.restaurantapp.demo.dto.requestDto.UserRequestDto;
import com.restaurantapp.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto dto);

    void updateEntity(UserRequestDto dto, @MappingTarget User entity);

    UserResponseDto toDto(User entity);

    List<UserResponseDto> toDto(List<User> entity);
}
