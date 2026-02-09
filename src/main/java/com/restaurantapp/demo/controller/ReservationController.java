package com.restaurantapp.demo.controller;

import com.restaurantapp.demo.dto.ResponseDto.ReservationDemandResponseDto;
import com.restaurantapp.demo.dto.ResponseDto.ReservationResponseDto;
import com.restaurantapp.demo.dto.requestDto.ReservationDemandRequestDto;
import com.restaurantapp.demo.dto.requestDto.ReservationRequestDto;
import com.restaurantapp.demo.service.ReservationManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationManagementService reservationManagementService;

    public ReservationController(ReservationManagementService reservationManagementService) {
        this.reservationManagementService = reservationManagementService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        return ResponseEntity.ok(reservationManagementService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationManagementService.getReservationById(id));
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(
            @Valid @RequestBody ReservationRequestDto dto
    ) {
        return ResponseEntity.ok(reservationManagementService.createReservation(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequestDto dto
    ) {
        return ResponseEntity.ok(reservationManagementService.updateReservation(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationManagementService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/demands")
    public ResponseEntity<List<ReservationDemandResponseDto>> getAllDemands() {
        return ResponseEntity.ok(reservationManagementService.getAllDemands());
    }

    @GetMapping("/demands/{id}")
    public ResponseEntity<ReservationDemandResponseDto> getDemandById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationManagementService.getDemandById(id));
    }

    @PostMapping("/demands")
    public ResponseEntity<ReservationDemandResponseDto> createDemand(
            @Valid @RequestBody ReservationDemandRequestDto dto
    ) {
        return ResponseEntity.ok(reservationManagementService.createDemand(dto));
    }

    @PutMapping("/demands/{id}")
    public ResponseEntity<ReservationDemandResponseDto> updateDemand(
            @PathVariable Long id,
            @Valid @RequestBody ReservationDemandRequestDto dto
    ) {
        return ResponseEntity.ok(reservationManagementService.updateDemand(id, dto));
    }

    @DeleteMapping("/demands/{id}")
    public ResponseEntity<Void> deleteDemand(@PathVariable Long id) {
        reservationManagementService.deleteDemand(id);
        return ResponseEntity.noContent().build();
    }
}
