package com.example.restaurant.mapper;

import com.example.restaurant.dto.request.StaffRequestDto;
import com.example.restaurant.dto.response.StaffResponseDto;
import com.example.restaurant.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StaffMapper {
    Staff toStaffEntity(StaffRequestDto staffDto);
    StaffResponseDto toStaffDto(Staff staff);
    List<StaffResponseDto> toStaffDto(List<Staff> staff);
}
