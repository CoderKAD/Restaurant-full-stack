package com.restaurantapp.demo.seeder;

import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.Role;
import com.restaurantapp.demo.repository.UserRepository;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class UserSeeder {

    private static final int DEFAULT_USER_COUNT = 12;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Faker faker;

    public UserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.faker = new Faker();
    }

    public List<User> seed() {
        if (userRepository.count() > 0) {
            return userRepository.findAll();
        }

        List<User> users = new ArrayList<>();
        Role[] roles = Role.values();

        for (int index = 0; index < DEFAULT_USER_COUNT; index++) {
            User user = new User();
            String firstName = faker.name().firstName().toLowerCase(Locale.ROOT);
            String lastName = faker.name().lastName().toLowerCase(Locale.ROOT);
            String suffix = String.valueOf(index + 1);

            user.setUsername(firstName + "." + lastName + suffix);
            user.setEmail(firstName + "." + lastName + suffix + "@restaurant.local");
            user.setPasswordHash(passwordEncoder.encode("Password123!"));
            user.setRoles(roles[index % roles.length]);
            users.add(user);
        }

        return userRepository.saveAll(users);
    }
}
