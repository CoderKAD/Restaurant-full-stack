package com.restaurantapp.demo.config;

import com.restaurantapp.demo.entity.Reservation;
import com.restaurantapp.demo.entity.RestaurantTable;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.ReservationStatus;
import com.restaurantapp.demo.repository.ReservationRepository;
import com.restaurantapp.demo.repository.RestaurantTableRepository;
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

@Configuration
public class ReservationDataSeeder {

    @Bean
    @Profile("!test")
    @Order(10)
    CommandLineRunner seedReservations(ReservationRepository reservationRepository,
                                       UserRepository userRepository,
                                       RestaurantTableRepository restaurantTableRepository) {
        return args -> {
            if (reservationRepository.count() > 0) {
                return;
            }

            List<User> users = userRepository.findAll();
            List<RestaurantTable> tables = restaurantTableRepository.findAll();
            if (users.isEmpty() || tables.isEmpty()) {
                return;
            }

            Faker faker = new Faker(Locale.ENGLISH);
            List<Reservation> reservations = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                User user = users.get(i % users.size());
                Reservation reservation = new Reservation();
                reservation.setPartySize(2 + (i % 4));
                reservation.setStartAt(LocalDate.now().plusDays(i + 1L));
                reservation.setEndAt(LocalDate.now().plusDays(i + 1L));
                reservation.setStatus(i % 2 == 0 ? ReservationStatus.PENDING : ReservationStatus.CONFIRMED);
                reservation.setNotes(faker.restaurant().review());
                reservation.setCreatedBy(user);
                reservation.setUpdatedBy(user);
                reservation.setTables(List.of(tables.get(i % tables.size())));
                reservations.add(reservation);
            }

            reservationRepository.saveAll(reservations);
        };
    }
}
