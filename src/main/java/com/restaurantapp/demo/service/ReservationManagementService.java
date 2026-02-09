package com.restaurantapp.demo.service;

import com.restaurantapp.demo.dto.ResponseDto.ReservationDemandResponseDto;
import com.restaurantapp.demo.dto.ResponseDto.ReservationResponseDto;
import com.restaurantapp.demo.dto.requestDto.ReservationDemandRequestDto;
import com.restaurantapp.demo.dto.requestDto.ReservationRequestDto;
import com.restaurantapp.demo.entity.Customer;
import com.restaurantapp.demo.entity.Reservation;
import com.restaurantapp.demo.entity.ReservationDemand;
import com.restaurantapp.demo.entity.RestaurantTable;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.repository.CustomerRepository;
import com.restaurantapp.demo.repository.ReservationDemandRepository;
import com.restaurantapp.demo.repository.ReservationRepository;
import com.restaurantapp.demo.repository.RestaurantTableRepository;
import com.restaurantapp.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationManagementService {
    private final ReservationRepository reservationRepository;
    private final ReservationDemandRepository reservationDemandRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public ReservationManagementService(ReservationRepository reservationRepository,
                                        ReservationDemandRepository reservationDemandRepository,
                                        RestaurantTableRepository restaurantTableRepository,
                                        UserRepository userRepository,
                                        CustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationDemandRepository = reservationDemandRepository;
        this.restaurantTableRepository = restaurantTableRepository;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll().stream().map(this::toReservationDto).toList();
    }

    public ReservationResponseDto getReservationById(Long id) {
        return toReservationDto(getReservationEntity(id));
    }

    public ReservationResponseDto createReservation(ReservationRequestDto dto) {
        Reservation reservation = new Reservation();
        applyReservationFields(dto, reservation);
        reservation.setId(null);
        reservation.setTables(resolveTables(dto.getTableIds()));
        if (dto.getCreatedById() != null) {
            reservation.setCreatedBy(getUserEntity(dto.getCreatedById()));
        }
        if (dto.getUpdatedById() != null) {
            reservation.setUpdatedBy(getUserEntity(dto.getUpdatedById()));
        }
        return toReservationDto(reservationRepository.save(reservation));
    }

    public ReservationResponseDto updateReservation(Long id, ReservationRequestDto dto) {
        Reservation existing = getReservationEntity(id);
        applyReservationFields(dto, existing);
        if (dto.getTableIds() != null) {
            existing.setTables(resolveTables(dto.getTableIds()));
        }
        if (dto.getCreatedById() != null) {
            existing.setCreatedBy(getUserEntity(dto.getCreatedById()));
        }
        if (dto.getUpdatedById() != null) {
            existing.setUpdatedBy(getUserEntity(dto.getUpdatedById()));
        }
        return toReservationDto(reservationRepository.save(existing));
    }

    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new EntityNotFoundException("Reservation not found with id: " + id);
        }
        reservationRepository.deleteById(id);
    }

    public List<ReservationDemandResponseDto> getAllDemands() {
        return reservationDemandRepository.findAll().stream().map(this::toDemandDto).toList();
    }

    public ReservationDemandResponseDto getDemandById(Long id) {
        return toDemandDto(getDemandEntity(id));
    }

    public ReservationDemandResponseDto createDemand(ReservationDemandRequestDto dto) {
        ReservationDemand demand = new ReservationDemand();
        demand.setStatus(dto.getStatus());
        demand.setReservation(getReservationEntity(dto.getReservationId()));
        demand.setCustomer(getCustomerEntity(dto.getCustomerId()));
        demand.setId(null);
        return toDemandDto(reservationDemandRepository.save(demand));
    }

    public ReservationDemandResponseDto updateDemand(Long id, ReservationDemandRequestDto dto) {
        ReservationDemand existing = getDemandEntity(id);
        existing.setStatus(dto.getStatus());
        existing.setReservation(getReservationEntity(dto.getReservationId()));
        existing.setCustomer(getCustomerEntity(dto.getCustomerId()));
        return toDemandDto(reservationDemandRepository.save(existing));
    }

    public void deleteDemand(Long id) {
        if (!reservationDemandRepository.existsById(id)) {
            throw new EntityNotFoundException("ReservationDemand not found with id: " + id);
        }
        reservationDemandRepository.deleteById(id);
    }

    private Reservation getReservationEntity(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
    }

    private ReservationDemand getDemandEntity(Long id) {
        return reservationDemandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReservationDemand not found with id: " + id));
    }

    private User getUserEntity(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private Customer getCustomerEntity(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    private List<RestaurantTable> resolveTables(List<Long> tableIds) {
        if (tableIds == null || tableIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<RestaurantTable> tables = restaurantTableRepository.findAllById(tableIds);
        if (tables.size() != tableIds.size()) {
            throw new EntityNotFoundException("One or more restaurant tables not found.");
        }
        return tables;
    }

    private void applyReservationFields(ReservationRequestDto dto, Reservation reservation) {
        reservation.setPartySize(dto.getPartySize());
        reservation.setStartAt(dto.getStartAt());
        reservation.setEndAt(dto.getEndAt());
        reservation.setStatus(dto.getStatus());
        reservation.setNotes(dto.getNotes());
    }

    private ReservationResponseDto toReservationDto(Reservation reservation) {
        List<Long> tableIds = reservation.getTables() == null ? Collections.emptyList()
                : reservation.getTables().stream().map(RestaurantTable::getId).toList();
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getPartySize(),
                reservation.getStartAt(),
                reservation.getEndAt(),
                reservation.getStatus(),
                reservation.getNotes(),
                reservation.getCreatedAt(),
                reservation.getUpdatedAt(),
                reservation.getCreatedBy() != null ? reservation.getCreatedBy().getId() : null,
                reservation.getUpdatedBy() != null ? reservation.getUpdatedBy().getId() : null,
                tableIds
        );
    }

    private ReservationDemandResponseDto toDemandDto(ReservationDemand demand) {
        return new ReservationDemandResponseDto(
                demand.getId(),
                demand.getStatus(),
                demand.getCreatedAt(),
                demand.getUpdatedAt(),
                demand.getReservation() != null ? demand.getReservation().getId() : null,
                demand.getCustomer() != null ? demand.getCustomer().getId() : null
        );
    }
}
