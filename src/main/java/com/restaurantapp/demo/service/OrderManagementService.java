package com.restaurantapp.demo.service;

import com.restaurantapp.demo.dto.ResponseDto.OrderItemResponseDto;
import com.restaurantapp.demo.dto.ResponseDto.OrderResponseDto;
import com.restaurantapp.demo.dto.ResponseDto.OrderWithItemsResponseDto;
import com.restaurantapp.demo.dto.requestDto.OrderItemRequestDto;
import com.restaurantapp.demo.dto.requestDto.OrderRequestDto;
import com.restaurantapp.demo.entity.MenuItem;
import com.restaurantapp.demo.entity.Order;
import com.restaurantapp.demo.entity.OrderItem;
import com.restaurantapp.demo.entity.RestaurantTable;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.mapper.OrderItemMapper;
import com.restaurantapp.demo.mapper.OrderMapper;
import com.restaurantapp.demo.repository.MenuItemRepository;
import com.restaurantapp.demo.repository.OrderItemRepository;
import com.restaurantapp.demo.repository.OrderRepository;
import com.restaurantapp.demo.repository.RestaurantTableRepository;
import com.restaurantapp.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class OrderManagementService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderManagementService(OrderRepository orderRepository,
                                  OrderItemRepository orderItemRepository,
                                  RestaurantTableRepository restaurantTableRepository,
                                  UserRepository userRepository,
                                  MenuItemRepository menuItemRepository,
                                  OrderMapper orderMapper,
                                  OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.restaurantTableRepository = restaurantTableRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toDto(orders);
    }

    public OrderResponseDto getOrderById(UUID id) {
        return orderMapper.toDto(getOrderEntity(id));
    }


    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Order entity = orderMapper.toEntity(dto);

        entity.setPublicCode(generatePublicCode());
        if (dto.getRestaurantTableId() != null) {
            entity.setRestaurantTable(getRestaurantTableEntity(dto.getRestaurantTableId()));
        } else {
            entity.setRestaurantTable(null);
        }
        if (dto.getCreatedById() != null) {
            entity.setCreatedBy(getUserEntity(dto.getCreatedById()));
        } else {
            entity.setCreatedBy(null);
        }
        if (dto.getUpdatedById() != null) {
            entity.setUpdatedBy(getUserEntity(dto.getUpdatedById()));
        } else {
            entity.setUpdatedBy(null);
        }
        Order saved = orderRepository.save(entity);
        return orderMapper.toDto(saved);
    }

    public OrderResponseDto updateOrder(UUID id, OrderRequestDto dto) {
        Order existing = getOrderEntity(id);
        orderMapper.updateEntity(dto, existing);
        if (dto.getRestaurantTableId() != null) {
            existing.setRestaurantTable(getRestaurantTableEntity(dto.getRestaurantTableId()));
        } else {
            existing.setRestaurantTable(null);
        }
        if (dto.getCreatedById() != null) {
            existing.setCreatedBy(getUserEntity(dto.getCreatedById()));
        } else {
            existing.setCreatedBy(null);
        }
        if (dto.getUpdatedById() != null) {
            existing.setUpdatedBy(getUserEntity(dto.getUpdatedById()));
        } else {
            existing.setUpdatedBy(null);
        }
        Order saved = orderRepository.save(existing);
        return orderMapper.toDto(saved);
    }

    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    public List<OrderItemResponseDto> getAllOrderItems() {
        List<OrderItem> items = orderItemRepository.findAll();
        return orderItemMapper.toDto(items);
    }

    public OrderItemResponseDto getOrderItemById(Long id) {
        return orderItemMapper.toDto(getOrderItemEntity(id));
    }

    public OrderItemResponseDto createOrderItem(OrderItemRequestDto dto) {
        OrderItem entity = orderItemMapper.toEntity(dto);
        entity.setId(null);
        entity.setOrder(getOrderEntity(dto.getOrderId()));
        entity.setMenuItem(getMenuItemEntity(dto.getMenuItemId()));
        OrderItem saved = orderItemRepository.save(entity);
        return orderItemMapper.toDto(saved);
    }

    public OrderItemResponseDto updateOrderItem(Long id, OrderItemRequestDto dto) {
        OrderItem existing = getOrderItemEntity(id);
        orderItemMapper.updateEntity(dto, existing);
        existing.setOrder(getOrderEntity(dto.getOrderId()));
        existing.setMenuItem(getMenuItemEntity(dto.getMenuItemId()));
        OrderItem saved = orderItemRepository.save(existing);
        return orderItemMapper.toDto(saved);
    }

    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new EntityNotFoundException("OrderItem not found with id: " + id);
        }
        orderItemRepository.deleteById(id);
    }

    private Order getOrderEntity(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    private OrderItem getOrderItemEntity(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found with id: " + id));
    }

    private RestaurantTable getRestaurantTableEntity(Long id) {
        return restaurantTableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RestaurantTable not found with id: " + id));
    }

    private User getUserEntity(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private MenuItem getMenuItemEntity(UUID id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MenuItem not found with id: " + id));
    }

    private OrderWithItemsResponseDto toOrderWithItemsResponse(Order order) {
        OrderResponseDto orderDto = orderMapper.toDto(order);
        List<OrderItemResponseDto> orderItems = orderItemMapper.toDto(order.getOrderItems());
        return new OrderWithItemsResponseDto(orderDto, orderItems);
    }

    private String generatePublicCode() {
        long count = orderRepository.count() + 1;
        return String.format("ORD-%04d", count);
    }
}
