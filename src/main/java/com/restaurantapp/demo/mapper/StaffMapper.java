package com.restaurantapp.demo.mapper;

import com.restaurantapp.demo.dto.ResponseDto.StaffResponseDto;
import com.restaurantapp.demo.dto.requestDto.StaffRequestDto;
import com.restaurantapp.demo.entity.Staff;
import com.restaurantapp.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserId")
    Staff toEntity(StaffRequestDto dto);

    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserId")
    void updateEntity(StaffRequestDto dto, @MappingTarget Staff entity);

    @Mapping(target = "userId", source = "user.id")
    StaffResponseDto toDto(Staff entity);

    @Mapping(target = "userId", source = "user.id")
    List<StaffResponseDto> toDto(List<Staff> entity);

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
