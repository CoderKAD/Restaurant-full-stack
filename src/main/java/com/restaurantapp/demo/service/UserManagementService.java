package com.restaurantapp.demo.service;

import com.restaurantapp.demo.dto.ResponseDto.UserResponseDto;
import com.restaurantapp.demo.dto.requestDto.UserRequestDto;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.Role;
import com.restaurantapp.demo.mapper.UserMapper;
import com.restaurantapp.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserManagementService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserManagementService(UserRepository userRepository,
                                 UserMapper userMapper,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDto(users);
    }



    public UserResponseDto createUser(UserRequestDto dto) {
        if (dto.getEmail() != null && userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (dto.getUsername() != null && userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = userMapper.toEntity(dto);
        user.setId(null);
        if (dto.getPasswordHash() != null && !dto.getPasswordHash().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPasswordHash()));
        }
        if (user.getRoles() == null) {
            user.setRoles(Role.CUSTOMER);
        }
        return userMapper.toDto(userRepository.save(user));
    }

    public UserResponseDto updateUser(UUID id, UserRequestDto dto) {
        User existing = getUserEntity(id);
        String currentPasswordHash = existing.getPasswordHash();
        userMapper.updateEntity(dto, existing);
        if (dto.getPasswordHash() != null && !dto.getPasswordHash().isBlank()) {
            existing.setPasswordHash(passwordEncoder.encode(dto.getPasswordHash()));
        } else {
            existing.setPasswordHash(currentPasswordHash);
        }
        if (existing.getRoles() == null) {
            existing.setRoles(Role.CUSTOMER);
        }
        return userMapper.toDto(userRepository.save(existing));
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private User getUserEntity(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}
