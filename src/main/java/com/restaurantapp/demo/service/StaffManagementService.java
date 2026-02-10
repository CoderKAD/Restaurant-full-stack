package com.restaurantapp.demo.service;

import com.restaurantapp.demo.dto.ResponseDto.StaffResponseDto;
import com.restaurantapp.demo.dto.requestDto.StaffRequestDto;
import com.restaurantapp.demo.entity.Staff;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.mapper.StaffMapper;
import com.restaurantapp.demo.repository.StaffRepository;
import com.restaurantapp.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StaffManagementService {
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final StaffMapper staffMapper;

    public StaffManagementService(StaffRepository staffRepository,
                                  UserRepository userRepository,
                                  StaffMapper staffMapper) {
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
        this.staffMapper = staffMapper;
    }

    public List<StaffResponseDto> getAllStaff() {
        List<Staff> staff = staffRepository.findAll();
        return staffMapper.toDto(staff);
    }


    public StaffResponseDto createStaff(StaffRequestDto dto) {
        Staff staff = staffMapper.toEntity(dto);
        staff.setId(null);
        if (dto.getUserId() != null) {
            staff.setUser(getUserEntity(dto.getUserId()));
        } else {
            staff.setUser(null);
        }
        Staff saved = staffRepository.save(staff);
        return staffMapper.toDto(saved);
    }

    public StaffResponseDto updateStaff(UUID id, StaffRequestDto dto) {
        Staff existing = getStaffEntity(id);
        staffMapper.updateEntity(dto, existing);
        if (dto.getUserId() != null) {
            existing.setUser(getUserEntity(dto.getUserId()));
        } else {
            existing.setUser(null);
        }
        Staff saved = staffRepository.save(existing);
        return staffMapper.toDto(saved);
    }

    public void deleteStaff(UUID id) {
        if (!staffRepository.existsById(id)) {
            throw new EntityNotFoundException("Staff not found with id: " + id);
        }
        staffRepository.deleteById(id);
    }

    private Staff getStaffEntity(UUID id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with id: " + id));
    }

    private User getUserEntity(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}
