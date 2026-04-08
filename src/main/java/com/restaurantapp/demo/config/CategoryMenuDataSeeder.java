package com.restaurantapp.demo.config;

import com.restaurantapp.demo.entity.CategoryMenu;
import com.restaurantapp.demo.repository.CategoryMenuRepository;
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
public class CategoryMenuDataSeeder {

    @Bean
    @Profile("!test")
    @Order(4)
    CommandLineRunner seedCategoryMenus(CategoryMenuRepository categoryMenuRepository) {
        return args -> {
            if (categoryMenuRepository.count() > 0) {
                return;
            }

            Faker faker = new Faker(Locale.ENGLISH);
            List<CategoryMenu> categories = new ArrayList<>();

            for (int i = 1; i <= 8; i++) {
                CategoryMenu category = new CategoryMenu();
                category.setCategoryName(faker.restaurant().type() + " " + i);
                category.setSortOrder(i);
                category.setActive(true);
                categories.add(category);
            }

            categoryMenuRepository.saveAll(categories);
        };
    }
}
