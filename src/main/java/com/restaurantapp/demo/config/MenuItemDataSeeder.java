package com.restaurantapp.demo.config;

import com.restaurantapp.demo.entity.CategoryMenu;
import com.restaurantapp.demo.entity.MenuItem;
import com.restaurantapp.demo.repository.CategoryMenuRepository;
import com.restaurantapp.demo.repository.MenuItemRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class MenuItemDataSeeder {

    @Bean
    @Profile("!test")
    @Order(5)
    CommandLineRunner seedMenuItems(MenuItemRepository menuItemRepository,
                                    CategoryMenuRepository categoryMenuRepository) {
        return args -> {
            if (menuItemRepository.count() > 0) {
                return;
            }

            List<CategoryMenu> categories = categoryMenuRepository.findAll();
            if (categories.isEmpty()) {
                return;
            }

            Faker faker = new Faker(Locale.ENGLISH);
            List<MenuItem> menuItems = new ArrayList<>();
            int sequence = 1;

            for (CategoryMenu category : categories) {
                for (int i = 0; i < 3; i++) {
                    MenuItem item = new MenuItem();
                    item.setName(faker.food().dish() + " " + sequence);
                    item.setDescription(faker.restaurant().description());
                    item.setPrice(Double.parseDouble(faker.commerce().price(5.0, 80.0)));
                    item.setActive(true);
                    item.setImageUrl(null);
                    item.setPrepStation("Kitchen");
                    item.setCategory(category);
                    menuItems.add(item);
                    sequence++;
                }
            }

            menuItemRepository.saveAll(menuItems);
        };
    }
}
