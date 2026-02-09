package com.restaurantapp.demo.service;

import com.restaurantapp.demo.dto.ResponseDto.CustomerResponseDto;
import com.restaurantapp.demo.dto.ResponseDto.StaffResponseDto;
import com.restaurantapp.demo.dto.ResponseDto.UserResponseDto;
import com.restaurantapp.demo.dto.requestDto.CustomerRequestDto;
import com.restaurantapp.demo.dto.requestDto.StaffRequestDto;
import com.restaurantapp.demo.dto.requestDto.UserRequestDto;
import com.restaurantapp.demo.entity.Customer;
import com.restaurantapp.demo.entity.Staff;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.repository.CustomerRepository;
import com.restaurantapp.demo.repository.StaffRepository;
import com.restaurantapp.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserProfileManagementService {
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;

    public UserProfileManagementService(UserRepository userRepository,
                                        StaffRepository staffRepository,
                                        CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.customerRepository = customerRepository;
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toUserDto).toList();
    }

    public UserResponseDto getUserById(UUID id) {
        return toUserDto(getUserEntity(id));
    }

    public UserResponseDto createUser(UserRequestDto dto) {
        User user = new User();
        applyUserFields(dto, user);
        user.setId(null);
        return toUserDto(userRepository.save(user));
    }

    public UserResponseDto updateUser(UUID id, UserRequestDto dto) {
        User existing = getUserEntity(id);
        applyUserFields(dto, existing);
        return toUserDto(userRepository.save(existing));
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public List<StaffResponseDto> getAllStaff() {
        return staffRepository.findAll().stream().map(this::toStaffDto).toList();
    }

    public StaffResponseDto getStaffById(UUID id) {
        return toStaffDto(getStaffEntity(id));
    }

    public StaffResponseDto createStaff(StaffRequestDto dto) {
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("User id is required for staff.");
        }
        Staff staff = new Staff();
        applyStaffFields(dto, staff);
        staff.setId(null);
        staff.setUser(getUserEntity(dto.getUserId()));
        return toStaffDto(staffRepository.save(staff));
    }

    public StaffResponseDto updateStaff(UUID id, StaffRequestDto dto) {
        Staff existing = getStaffEntity(id);
        applyStaffFields(dto, existing);
        if (dto.getUserId() != null) {
            existing.setUser(getUserEntity(dto.getUserId()));
        }
        return toStaffDto(staffRepository.save(existing));
    }

    public void deleteStaff(UUID id) {
        if (!staffRepository.existsById(id)) {
            throw new EntityNotFoundException("Staff not found with id: " + id);
        }
        staffRepository.deleteById(id);
    }

    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::toCustomerDto).toList();
    }

    public CustomerResponseDto getCustomerById(UUID id) {
        return toCustomerDto(getCustomerEntity(id));
    }

    public CustomerResponseDto createCustomer(CustomerRequestDto dto) {
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("User id is required for customer.");
        }
        Customer customer = new Customer();
        applyCustomerFields(dto, customer);
        customer.setId(null);
        customer.setUser(getUserEntity(dto.getUserId()));
        return toCustomerDto(customerRepository.save(customer));
    }

    public CustomerResponseDto updateCustomer(UUID id, CustomerRequestDto dto) {
        Customer existing = getCustomerEntity(id);
        applyCustomerFields(dto, existing);
        if (dto.getUserId() != null) {
            existing.setUser(getUserEntity(dto.getUserId()));
        }
        return toCustomerDto(customerRepository.save(existing));
    }

    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    private User getUserEntity(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private Staff getStaffEntity(UUID id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with id: " + id));
    }

    private Customer getCustomerEntity(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    private void applyUserFields(UserRequestDto dto, User user) {
        user.setUsername(dto.getUsername());
        user.setPasswordHash(dto.getPasswordHash());
        user.setEmail(dto.getEmail());
        user.setRoles(dto.getRoles());
    }

    private void applyStaffFields(StaffRequestDto dto, Staff staff) {
        staff.setFirstName(dto.getFirstName());
        staff.setLastName(dto.getLastName());
        staff.setSalary(dto.getSalary());
        staff.setPosition(dto.getPosition());
        staff.setDateJoined(dto.getDateJoined());
        staff.setDateLeft(dto.getDateLeft());
        staff.setCin(dto.getCin());
    }

    private void applyCustomerFields(CustomerRequestDto dto, Customer customer) {
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
    }

    private UserResponseDto toUserDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    private StaffResponseDto toStaffDto(Staff staff) {
        return new StaffResponseDto(
                staff.getId(),
                staff.getFirstName(),
                staff.getLastName(),
                staff.getSalary(),
                staff.getPosition(),
                staff.getDateJoined(),
                staff.getDateLeft(),
                staff.getCin(),
                staff.getCreatedAt(),
                staff.getUpdatedAt(),
                staff.getUser() != null ? staff.getUser().getId() : null
        );
    }

    private CustomerResponseDto toCustomerDto(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getUser() != null ? customer.getUser().getId() : null
        );
    }
}
