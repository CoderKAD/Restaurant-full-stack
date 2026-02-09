package com.restaurantapp.demo.service;

import com.restaurantapp.demo.dto.ResponseDto.RestaurantTableResponseDto;
import com.restaurantapp.demo.dto.requestDto.RestaurantTableRequestDto;
import com.restaurantapp.demo.entity.RestaurantTable;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.mapper.RestaurantTableMapper;
import com.restaurantapp.demo.repository.RestaurantTableRepository;
import com.restaurantapp.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;
    private final UserRepository userRepository;
    private final RestaurantTableMapper restaurantTableMapper;

    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository,
                                  UserRepository userRepository,
                                  RestaurantTableMapper restaurantTableMapper) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.userRepository = userRepository;
        this.restaurantTableMapper = restaurantTableMapper;
    }

    public List<RestaurantTableResponseDto> getAllTables() {
        List<RestaurantTable> tables = restaurantTableRepository.findAll();
        return restaurantTableMapper.toDto(tables);
    }

    public RestaurantTableResponseDto getTableById(Long id) {
        return restaurantTableMapper.toDto(getTableEntity(id));
    }

    public RestaurantTableResponseDto createTable(RestaurantTableRequestDto dto) {
        RestaurantTable entity = restaurantTableMapper.toEntity(dto);
        entity.setId(null);
        if (dto.getUserId() != null) {
            entity.setUser(getUserEntity(dto.getUserId()));
        } else {
            entity.setUser(null);
        }
        RestaurantTable saved = restaurantTableRepository.save(entity);
        return restaurantTableMapper.toDto(saved);
    }

    public RestaurantTableResponseDto updateTable(Long id, RestaurantTableRequestDto dto) {
        RestaurantTable existing = getTableEntity(id);
        restaurantTableMapper.updateEntity(dto, existing);
        if (dto.getUserId() != null) {
            existing.setUser(getUserEntity(dto.getUserId()));
        } else {
            existing.setUser(null);
        }
        RestaurantTable saved = restaurantTableRepository.save(existing);
        return restaurantTableMapper.toDto(saved);
    }

    public void deleteTable(Long id) {
        if (!restaurantTableRepository.existsById(id)) {
            throw new EntityNotFoundException("RestaurantTable not found with id: " + id);
        }
        restaurantTableRepository.deleteById(id);
    }

    private RestaurantTable getTableEntity(Long id) {
        return restaurantTableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RestaurantTable not found with id: " + id));
    }

    private User getUserEntity(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}
