package com.restaurantapp.demo.config;

import com.restaurantapp.demo.entity.Staff;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.Role;
import com.restaurantapp.demo.repository.StaffRepository;
import com.restaurantapp.demo.repository.UserRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Configuration
public class StaffDataSeeder {

    @Bean
    @Profile("!test")
    @Order(3)
    CommandLineRunner seedStaff(StaffRepository staffRepository, UserRepository userRepository) {
        return args -> {
            if (staffRepository.count() > 0) {
                return;
            }

            List<User> staffUsers = userRepository.findAll().stream()
                    .filter(user -> user.getRoles() == Role.ADMIN
                            || user.getRoles() == Role.CASHIER
                            || user.getRoles() == Role.KITCHEN
                            || user.getRoles() == Role.DELIVERY)
                    .collect(Collectors.toList());
            if (staffUsers.isEmpty()) {
                return;
            }

            Faker faker = new Faker(Locale.ENGLISH);
            List<Staff> staffMembers = new ArrayList<>();

            for (int i = 0; i < staffUsers.size(); i++) {
                Staff staff = new Staff();
                staff.setFirstName(faker.name().firstName());
                staff.setLastName(faker.name().lastName());
                staff.setSalary(4500.0 + (i * 700));
                staff.setPosition(staffUsers.get(i).getRoles().name());
                staff.setDateJoined(LocalDate.now().minusDays(30L + i));
                staff.setCin("SEEDCIN" + (1000 + i));
                staff.setUser(staffUsers.get(i));
                staffMembers.add(staff);
            }

            staffRepository.saveAll(staffMembers);
        };
    }
}
