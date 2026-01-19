package com.example.restaurant.mapper;

import com.example.restaurant.dto.request.ReservationRequestDto;
import com.example.restaurant.dto.response.ReservationResponseDto;
import com.example.restaurant.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {
    Reservation toReservationEntity(ReservationRequestDto reservationDto);
    ReservationResponseDto toReservationDto(Reservation reservation);
    List<ReservationResponseDto> toReservationDto(List<Reservation> reservation);
}
