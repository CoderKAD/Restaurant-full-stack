package com.restaurantapp.demo.config;

import com.restaurantapp.demo.entity.RestaurantTable;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.TableStatus;
import com.restaurantapp.demo.repository.RestaurantTableRepository;
import com.restaurantapp.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestaurantTableDataSeeder {

    @Bean
    @Profile("!test")
    @Order(2)
    CommandLineRunner seedRestaurantTables(RestaurantTableRepository restaurantTableRepository,
                                           UserRepository userRepository) {
        return args -> {
            if (restaurantTableRepository.count() > 0) {
                return;
            }

            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                return;
            }

            List<RestaurantTable> tables = new ArrayList<>();
            for (int i = 1; i <= 8; i++) {
                RestaurantTable table = new RestaurantTable();
                table.setLabel("T" + i);
                table.setSeats(i % 2 == 0 ? 4 : 2);
                table.setPublicCode("TB-SEED-" + i);
                table.setActive(true);
                table.setStatus(i % 3 == 0 ? TableStatus.Reserved : TableStatus.Available);
                table.setUser(users.get((i - 1) % users.size()));
                tables.add(table);
            }

            restaurantTableRepository.saveAll(tables);
        };
    }
}
