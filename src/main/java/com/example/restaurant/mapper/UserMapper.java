package com.example.restaurant.mapper;


import com.example.restaurant.dto.request.UserRequestDto;
import com.example.restaurant.dto.response.UserResponseDto;
import com.example.restaurant.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface UserMapper {
    User toUserEntity(UserRequestDto userDto);
    List<UserResponseDto> toUserDto(List<User> user);
    UserResponseDto toUserDto(User user);
}
