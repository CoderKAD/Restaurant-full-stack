package com.restaurantapp.demo.config;

import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.Role;
import com.restaurantapp.demo.repository.UserRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class UserDataSeeder {

    @Bean
    @Profile("!test")
    @Order(1)
    CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }

            Faker faker = new Faker(Locale.ENGLISH);
            Role[] roles = {Role.ADMIN, Role.CUSTOMER, Role.CASHIER, Role.KITCHEN, Role.DELIVERY};
            List<User> users = new ArrayList<>();

            for (int i = 1; i <= 8; i++) {
                User user = new User();
                user.setUsername("seeduser" + i);
                user.setEmail("seeduser" + i + "@example.com");
                user.setPasswordHash(passwordEncoder.encode("Seed@123"));
                user.setRoles(roles[(i - 1) % roles.length]);
                users.add(user);
            }

            users.add(createFakeCustomerUser(faker, passwordEncoder, 9));
            users.add(createFakeCustomerUser(faker, passwordEncoder, 10));

            userRepository.saveAll(users);
        };
    }

    private User createFakeCustomerUser(Faker faker, PasswordEncoder passwordEncoder, int index) {
        User user = new User();
        user.setUsername(faker.name().username() + index);
        user.setEmail("customer.seed" + index + "@example.com");
        user.setPasswordHash(passwordEncoder.encode("Seed@123"));
        user.setRoles(Role.CUSTOMER);
        return user;
    }
}
