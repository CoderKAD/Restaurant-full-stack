package com.restaurantapp.demo.dto.ResponseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurantapp.demo.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private UUID id;
    private String username;
    private String email;
    private Role roles;
    private String passwordHash;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime updatedAt;
}
