package com.restaurantapp.demo.controller;

import com.restaurantapp.demo.dto.ResponseDto.UserResponseDto;
import com.restaurantapp.demo.dto.requestDto.UserRequestDto;
import com.restaurantapp.demo.service.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserManagementService userManagementService;

    public UserController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }


    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userManagementService.createUser(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserRequestDto dto
    ) {
        return ResponseEntity.ok(userManagementService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userManagementService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
