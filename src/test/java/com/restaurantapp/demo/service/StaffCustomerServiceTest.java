package com.restaurantapp.demo.service;

import com.restaurantapp.demo.dto.requestDto.StaffRequestDto;
import com.restaurantapp.demo.mapper.CustomerMapper;
import com.restaurantapp.demo.mapper.StaffMapper;
import com.restaurantapp.demo.repository.CustomerRepository;
import com.restaurantapp.demo.repository.StaffRepository;
import com.restaurantapp.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StaffCustomerServiceTest {

    @Mock
    private StaffRepository staffRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private StaffMapper staffMapper;
    @Mock
    private CustomerMapper customerMapper;

    private StaffCustomerService staffCustomerService;

    @BeforeEach
    void setUp() {
        staffCustomerService = new StaffCustomerService(
                staffRepository,
                customerRepository,
                userRepository,
                staffMapper,
                customerMapper
        );
    }

    @Test
    void createStaff_shouldThrowError_whenCinAlreadyExistsInStaff() {
        StaffRequestDto dto = new StaffRequestDto();
        dto.setCin("AB123456");

        when(staffRepository.existsByCinIgnoreCase("AB123456", id)).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> staffCustomerService.createStaff(dto)
        );

        assertEquals("CIN already exists: AB123456", ex.getMessage());
        verify(staffRepository, never()).save(any());
    }

}
