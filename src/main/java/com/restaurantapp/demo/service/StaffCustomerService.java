package com.restaurantapp.demo.service;

import com.restaurantapp.demo.dto.ResponseDto.CustomerResponseDto;
import com.restaurantapp.demo.dto.ResponseDto.StaffResponseDto;
import com.restaurantapp.demo.dto.requestDto.CustomerRequestDto;
import com.restaurantapp.demo.dto.requestDto.StaffRequestDto;
import com.restaurantapp.demo.entity.Customer;
import com.restaurantapp.demo.entity.Staff;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.mapper.CustomerMapper;
import com.restaurantapp.demo.mapper.StaffMapper;
import com.restaurantapp.demo.repository.CustomerRepository;
import com.restaurantapp.demo.repository.StaffRepository;
import com.restaurantapp.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StaffCustomerService {

    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final StaffMapper staffMapper;
    private final CustomerMapper customerMapper;

    public StaffCustomerService(StaffRepository staffRepository,
                                CustomerRepository customerRepository,
                                UserRepository userRepository,
                                StaffMapper staffMapper,
                                CustomerMapper customerMapper) {
        this.staffRepository = staffRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.staffMapper = staffMapper;
        this.customerMapper = customerMapper;
    }

    public List<StaffResponseDto> getAllStaff() {

        return staffMapper.toDto(staffRepository.findAll());
    }

    public StaffResponseDto createStaff(StaffRequestDto dto) {
        validateStaffCinForCreate(dto.getCin());
        Staff entity = staffMapper.toEntity(dto);
        entity.setUser(getOptionalUserEntity(dto.getUserId()));
        return staffMapper.toDto(staffRepository.save(entity));
    }

    public StaffResponseDto updateStaff(UUID id, StaffRequestDto dto) {
        Staff existing = getStaffEntity(id);
        validateStaffCinForUpdate(dto.getCin(), id);
        staffMapper.updateEntity(dto, existing);
        existing.setUser(getOptionalUserEntity(dto.getUserId()));
        return staffMapper.toDto(staffRepository.save(existing));
    }

    public void deleteStaff(UUID id) {
        if (!staffRepository.existsById(id)) {
            throw new EntityNotFoundException("Staff not found with id: " + id);
        }
        staffRepository.deleteById(id);
    }

    public List<CustomerResponseDto> getAllCustomers() {
        return customerMapper.toDto(customerRepository.findAll());
    }

    public CustomerResponseDto createCustomer(CustomerRequestDto dto) {
        User user = getUserEntity(dto.getUserId());
        Customer entity = customerMapper.toEntity(dto);
        entity.setUser(user);
        return customerMapper.toDto(customerRepository.save(entity));
    }

    public CustomerResponseDto updateCustomer(UUID id, CustomerRequestDto dto) {
        Customer existing = getCustomerEntity(id);
        User user = getUserEntity(dto.getUserId());
        customerMapper.updateEntity(dto, existing);
        existing.setUser(user);
        return customerMapper.toDto(customerRepository.save(existing));
    }

    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    private void validateStaffCinForCreate(String cin ) {
        if (staffRepository.existsByCinIgnoreCase(cin )) {
            throw new IllegalArgumentException("CIN already exists: " + cin);
        }
    }

    private void validateStaffCinForUpdate(String cin, UUID staffId) {
        if (staffRepository.existsByCinIgnoreCaseAndIdNot(cin, staffId)) {
            throw new IllegalArgumentException("CIN already exists: " + cin);
        }
    }

    private User getUserEntity(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private User getOptionalUserEntity(UUID id) {
        if (id == null) {
            return null;
        }
        return getUserEntity(id);
    }

    private Staff getStaffEntity(UUID id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with id: " + id));
    }

    private Customer getCustomerEntity(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }
}
