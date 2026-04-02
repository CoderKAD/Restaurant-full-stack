package com.restaurantapp.demo.controller;

import com.restaurantapp.demo.dto.ResponseDto.CustomerResponseDto;
import com.restaurantapp.demo.dto.ResponseDto.StaffResponseDto;
import com.restaurantapp.demo.dto.requestDto.CustomerRequestDto;
import com.restaurantapp.demo.dto.requestDto.StaffRequestDto;
import com.restaurantapp.demo.service.StaffCustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class StaffCustomerController {
    private final StaffCustomerService staffCustomerService;

    public StaffCustomerController(StaffCustomerService staffCustomerService) {
        this.staffCustomerService = staffCustomerService;
    }

    @GetMapping("/staff")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StaffResponseDto>> getAllStaff() {
        return ResponseEntity.ok(staffCustomerService.getAllStaff());
    }

    @PostMapping("/staff")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StaffResponseDto> createStaff(@Valid @RequestBody StaffRequestDto dto) {
        return ResponseEntity.ok(staffCustomerService.createStaff(dto));
    }

    @PutMapping("/staff/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StaffResponseDto> updateStaff(
            @PathVariable UUID id,
            @Valid @RequestBody StaffRequestDto dto
    ) {
        return ResponseEntity.ok(staffCustomerService.updateStaff(id, dto));
    }

    @DeleteMapping("/staff/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStaff(@PathVariable UUID id) {
        staffCustomerService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        return ResponseEntity.ok(staffCustomerService.getAllCustomers());
    }

    @PostMapping("/customers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto dto) {
        return ResponseEntity.ok(staffCustomerService.createCustomer(dto));
    }

    @PutMapping("/customers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerRequestDto dto
    ) {
        return ResponseEntity.ok(staffCustomerService.updateCustomer(id, dto));
    }

    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        staffCustomerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
