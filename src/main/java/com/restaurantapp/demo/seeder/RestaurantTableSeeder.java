package com.restaurantapp.demo.seeder;

import com.restaurantapp.demo.entity.RestaurantTable;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.Role;
import com.restaurantapp.demo.entity.enums.TableStatus;
import com.restaurantapp.demo.repository.RestaurantTableRepository;
import com.restaurantapp.demo.util.PublicCodeGenerator;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantTableSeeder {

    private static final int DEFAULT_TABLE_COUNT = 10;

    private final RestaurantTableRepository restaurantTableRepository;
    private final Faker faker;

    public RestaurantTableSeeder(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.faker = new Faker();
    }

    public List<RestaurantTable> seed(List<User> users) {
        if (restaurantTableRepository.count() > 0) {
            return restaurantTableRepository.findAll();
        }

        List<User> managers = users.stream()
                .filter(user -> user.getRoles() == Role.ADMIN || user.getRoles() == Role.CASHIER)
                .collect(Collectors.toList());

        List<RestaurantTable> tables = new ArrayList<>();
        TableStatus[] statuses = TableStatus.values();

        for (int index = 0; index < DEFAULT_TABLE_COUNT; index++) {
            RestaurantTable table = new RestaurantTable();
            table.setLabel("Table " + (index + 1));
            table.setSeats(faker.options().option(2, 4, 4, 6, 8));
            table.setPublicCode(PublicCodeGenerator.generateTableCode(index + 1L, restaurantTableRepository::existsByPublicCode));
            table.setActive(Boolean.TRUE);
            table.setStatus(statuses[index % statuses.length]);
            if (!managers.isEmpty()) {
                table.setUser(managers.get(index % managers.size()));
            }
            tables.add(table);
        }

        return restaurantTableRepository.saveAll(tables);
    }
}
