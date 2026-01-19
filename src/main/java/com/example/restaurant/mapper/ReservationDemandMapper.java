package com.example.restaurant.mapper;

import com.example.restaurant.dto.request.ReservationDemandRequestDto;
import com.example.restaurant.dto.response.ReservationResponseDto;
import com.example.restaurant.entity.ReservationDemand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface ReservationDemandMapper {
    ReservationDemand toReservationDemandEntity(ReservationDemandRequestDto reservationDto);
    ReservationResponseDto toReservationDemandDto(ReservationDemand reservationDemand);
    List<ReservationResponseDto> toReservationDemandDto(List<ReservationDemand> reservationDemand);

}
