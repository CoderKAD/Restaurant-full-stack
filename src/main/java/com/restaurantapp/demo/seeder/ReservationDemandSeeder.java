package com.restaurantapp.demo.seeder;

import com.restaurantapp.demo.entity.Reservation;
import com.restaurantapp.demo.entity.ReservationDemand;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.DemandStatus;
import com.restaurantapp.demo.repository.ReservationDemandRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ReservationDemandSeeder {

    private final ReservationDemandRepository reservationDemandRepository;
    private final Faker faker;

    public ReservationDemandSeeder(ReservationDemandRepository reservationDemandRepository) {
        this.reservationDemandRepository = reservationDemandRepository;
        this.faker = new Faker();
    }

    public List<ReservationDemand> seed(List<Reservation> reservations, List<User> users) {
        if (reservationDemandRepository.count() > 0) {
            return reservationDemandRepository.findAll();
        }

        if (reservations.isEmpty() || users.isEmpty()) {
            return Collections.emptyList();
        }

        List<ReservationDemand> demands = new ArrayList<>();
        DemandStatus[] statuses = DemandStatus.values();

        for (int index = 0; index < reservations.size(); index++) {
            ReservationDemand demand = new ReservationDemand();
            demand.setReservation(reservations.get(index));
            demand.setUser(users.get(faker.number().numberBetween(0, users.size())));
            demand.setStatus(statuses[index % statuses.length]);
            demands.add(demand);
        }

        return reservationDemandRepository.saveAll(demands);
    }
}
