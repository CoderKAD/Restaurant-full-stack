package com.restaurantapp.demo.config;

import com.restaurantapp.demo.entity.Customer;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.Role;
import com.restaurantapp.demo.repository.CustomerRepository;
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
import java.util.stream.Collectors;

@Configuration
public class CustomerDataSeeder {

    @Bean
    @Profile("!test")
    @Order(6)
    CommandLineRunner seedCustomers(CustomerRepository customerRepository, UserRepository userRepository) {
        return args -> {
            if (customerRepository.count() > 0) {
                return;
            }

            List<User> customerUsers = userRepository.findAll().stream()
                    .filter(user -> user.getRoles() == Role.CUSTOMER)
                    .collect(Collectors.toList());
            if (customerUsers.isEmpty()) {
                return;
            }

            Faker faker = new Faker(Locale.ENGLISH);
            List<Customer> customers = new ArrayList<>();

            for (int i = 0; i < customerUsers.size(); i++) {
                Customer customer = new Customer();
                customer.setFirstName(faker.name().firstName());
                customer.setLastName(faker.name().lastName());
                customer.setPhone("06" + (10000000 + i));
                customer.setAddress(faker.address().fullAddress());
                customer.setUser(customerUsers.get(i));
                customers.add(customer);
            }

            customerRepository.saveAll(customers);
        };
    }
}
