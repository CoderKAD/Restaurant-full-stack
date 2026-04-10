package com.restaurantapp.demo.seeder;

import com.restaurantapp.demo.entity.Staff;
import com.restaurantapp.demo.entity.User;
import com.restaurantapp.demo.entity.enums.Role;
import com.restaurantapp.demo.repository.StaffRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class StaffSeeder {

    private final StaffRepository staffRepository;
    private final Faker faker;

    public StaffSeeder(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
        this.faker = new Faker();
    }

    public List<Staff> seed(List<User> users) {
        if (staffRepository.count() > 0) {
            return staffRepository.findAll();
        }

        List<User> staffUsers = users.stream()
                .filter(user -> user.getRoles() != Role.CUSTOMER)
                .collect(Collectors.toList());

        List<Staff> staffMembers = new ArrayList<>();
        for (int index = 0; index < staffUsers.size(); index++) {
            User user = staffUsers.get(index);
            Staff staff = new Staff();
            staff.setFirstName(faker.name().firstName());
            staff.setLastName(faker.name().lastName());
            staff.setSalary(faker.number().randomDouble(2, 3500, 12000));
            staff.setPosition(resolvePosition(user.getRoles()));
            staff.setDateJoined(LocalDate.now().minusDays(faker.number().numberBetween(30, 1200)));
            staff.setCin(buildCin(index));
            staff.setUser(user);
            staffMembers.add(staff);
        }

        return staffRepository.saveAll(staffMembers);
    }

    private String resolvePosition(Role role) {
        return switch (role) {
            case ADMIN -> "Restaurant Manager";
            case KITCHEN -> "Chef de Partie";
            case CASHIER -> "Cashier";
            case DELIVERY -> "Delivery Rider";
            case CUSTOMER -> "Support";
        };
    }

    private String buildCin(int index) {
        String letters = faker.regexify("[A-Z]{2}");
        return letters + String.format(Locale.ROOT, "%06d", index + 1);
    }
}
