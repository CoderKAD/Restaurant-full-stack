package com.restaurantapp.demo.seeder;

import com.restaurantapp.demo.entity.Reservation;
import com.restaurantapp.demo.entity.RestaurantTable;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.ReservationStatus;
import com.restaurantapp.demo.repository.ReservationRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ReservationSeeder {

    private static final int DEFAULT_RESERVATION_COUNT = 8;

    private final ReservationRepository reservationRepository;
    private final Faker faker;

    public ReservationSeeder(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
        this.faker = new Faker();
    }

    public List<Reservation> seed(List<User> users, List<RestaurantTable> tables) {
        if (reservationRepository.count() > 0) {
            return reservationRepository.findAll();
        }

        if (users.isEmpty() || tables.isEmpty()) {
            return Collections.emptyList();
        }

        List<Reservation> reservations = new ArrayList<>();
        ReservationStatus[] statuses = ReservationStatus.values();

        for (int index = 0; index < DEFAULT_RESERVATION_COUNT; index++) {
            Reservation reservation = new Reservation();
            LocalDate startDate = LocalDate.now().plusDays(index + 1L);
            User owner = users.get(index % users.size());
            RestaurantTable table = tables.get(index % tables.size());

            reservation.setPartySize(faker.number().numberBetween(2, Math.max(3, table.getSeats() + 1)));
            reservation.setStartAt(startDate);
            reservation.setEndAt(startDate.plusDays(faker.number().numberBetween(0, 2)));
            reservation.setStatus(statuses[index % statuses.length]);
            reservation.setNotes(faker.lorem().sentence(10));
            reservation.setCreatedBy(owner);
            reservation.setUpdatedBy(users.get((index + 1) % users.size()));
            reservation.setTables(List.of(table));
            reservations.add(reservation);
        }

        return reservationRepository.saveAll(reservations);
    }
}
