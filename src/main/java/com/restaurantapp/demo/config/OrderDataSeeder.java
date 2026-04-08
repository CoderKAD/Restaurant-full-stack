package com.restaurantapp.demo.config;

import com.restaurantapp.demo.entity.Order;
import com.restaurantapp.demo.entity.RestaurantTable;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.OrderStatus;
import com.restaurantapp.demo.entity.enums.OrderType;
import com.restaurantapp.demo.entity.enums.PaymentStatus;
import com.restaurantapp.demo.repository.OrderRepository;
import com.restaurantapp.demo.repository.RestaurantTableRepository;
import com.restaurantapp.demo.repository.UserRepository;
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
public class OrderDataSeeder {

    @Bean
    @Profile("!test")
    @Order(8)
    CommandLineRunner seedOrders(OrderRepository orderRepository,
                                 UserRepository userRepository,
                                 RestaurantTableRepository restaurantTableRepository) {
        return args -> {
            if (orderRepository.count() > 0) {
                return;
            }

            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                return;
            }

            List<RestaurantTable> tables = restaurantTableRepository.findAll();
            Faker faker = new Faker(Locale.ENGLISH);
            List<Order> orders = new ArrayList<>();

            for (int i = 0; i < 6; i++) {
                User user = users.get(i % users.size());
                Order order = new Order();
                order.setPublicCode("ORD-SEED-" + (i + 1));
                order.setStatus(OrderStatus.PREPARING);
                order.setPaymentStatus(i % 2 == 0 ? PaymentStatus.PENDING : PaymentStatus.PAID);
                order.setNotes(faker.restaurant().review());
                order.setCreatedBy(user);
                order.setUpdatedBy(user);

                if (!tables.isEmpty() && i % 2 == 0) {
                    order.setTypeOrder(OrderType.DINE_IN);
                    order.setRestaurantTable(tables.get(i % tables.size()));
                    order.setDeliveryAddress(null);
                } else {
                    order.setTypeOrder(OrderType.DELIVERY);
                    order.setDeliveryAddress(faker.address().fullAddress());
                }

                orders.add(order);
            }

            orderRepository.saveAll(orders);
        };
    }
}
