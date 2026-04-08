package com.restaurantapp.demo.config;

import com.restaurantapp.demo.entity.Customer;
import com.restaurantapp.demo.entity.Reservation;
import com.restaurantapp.demo.entity.ReservationDemand;
import com.restaurantapp.demo.entity.enums.DemandStatus;
import com.restaurantapp.demo.repository.CustomerRepository;
import com.restaurantapp.demo.repository.ReservationDemandRepository;
import com.restaurantapp.demo.repository.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ReservationDemandDataSeeder {

    @Bean
    @Profile("!test")
    @Order(11)
    CommandLineRunner seedReservationDemands(ReservationDemandRepository reservationDemandRepository,
                                             ReservationRepository reservationRepository,
                                             CustomerRepository customerRepository) {
        return args -> {
            if (reservationDemandRepository.count() > 0) {
                return;
            }

            List<Reservation> reservations = reservationRepository.findAll();
            List<Customer> customers = customerRepository.findAll();
            if (reservations.isEmpty() || customers.isEmpty()) {
                return;
            }

            List<ReservationDemand> demands = new ArrayList<>();
            for (int i = 0; i < Math.min(reservations.size(), customers.size()); i++) {
                ReservationDemand demand = new ReservationDemand();
                demand.setStatus(i % 2 == 0 ? DemandStatus.PENDING : DemandStatus.ACCEPTED);
                demand.setReservation(reservations.get(i));
                demand.setCustomer(customers.get(i));
                demands.add(demand);
            }

            reservationDemandRepository.saveAll(demands);
        };
    }
}
