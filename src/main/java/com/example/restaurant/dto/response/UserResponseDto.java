package com.example.restaurant.dto.response;

import com.example.restaurant.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class UserResponseDto {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Role roles;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
