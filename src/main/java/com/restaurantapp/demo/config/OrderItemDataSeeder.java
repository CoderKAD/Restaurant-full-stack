package com.restaurantapp.demo.config;

import com.restaurantapp.demo.entity.MenuItem;
import com.restaurantapp.demo.entity.Order;
import com.restaurantapp.demo.entity.OrderItem;
import com.restaurantapp.demo.repository.MenuItemRepository;
import com.restaurantapp.demo.repository.OrderItemRepository;
import com.restaurantapp.demo.repository.OrderRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class OrderItemDataSeeder {

    @Bean
    @Profile("!test")
    @Order(9)
    CommandLineRunner seedOrderItems(OrderItemRepository orderItemRepository,
                                     OrderRepository orderRepository,
                                     MenuItemRepository menuItemRepository) {
        return args -> {
            if (orderItemRepository.count() > 0) {
                return;
            }

            List<Order> orders = orderRepository.findAll();
            List<MenuItem> menuItems = menuItemRepository.findAll();
            if (orders.isEmpty() || menuItems.isEmpty()) {
                return;
            }

            Faker faker = new Faker(Locale.ENGLISH);
            List<OrderItem> orderItems = new ArrayList<>();

            for (int i = 0; i < orders.size(); i++) {
                for (int j = 0; j < 2; j++) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(orders.get(i));
                    orderItem.setMenuItem(menuItems.get((i + j) % menuItems.size()));
                    orderItem.setQuantity(faker.number().numberBetween(1, 4));
                    orderItem.setNotes(faker.lorem().sentence(4));
                    orderItems.add(orderItem);
                }
            }

            orderItemRepository.saveAll(orderItems);
        };
    }
}
