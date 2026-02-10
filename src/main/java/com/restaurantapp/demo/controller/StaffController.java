package com.restaurantapp.demo.controller;

import com.restaurantapp.demo.dto.ResponseDto.StaffResponseDto;
import com.restaurantapp.demo.dto.requestDto.StaffRequestDto;
import com.restaurantapp.demo.service.StaffManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    private final StaffManagementService staffManagementService;

    public StaffController(StaffManagementService staffManagementService) {
        this.staffManagementService = staffManagementService;
    }

    @GetMapping
    public ResponseEntity<List<StaffResponseDto>> getAllStaff() {
        return ResponseEntity.ok(staffManagementService.getAllStaff());
    }

    @PostMapping
    public ResponseEntity<StaffResponseDto> createStaff(@Valid @RequestBody StaffRequestDto dto) {
        return ResponseEntity.ok(staffManagementService.createStaff(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffResponseDto> updateStaff(
            @PathVariable UUID id,
            @Valid @RequestBody StaffRequestDto dto
    ) {
        return ResponseEntity.ok(staffManagementService.updateStaff(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable UUID id) {
        staffManagementService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
