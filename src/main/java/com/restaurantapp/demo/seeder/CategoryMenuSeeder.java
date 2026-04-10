package com.restaurantapp.demo.seeder;

import com.restaurantapp.demo.entity.CategoryMenu;
import com.restaurantapp.demo.repository.CategoryMenuRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMenuSeeder {

    private static final int DEFAULT_CATEGORY_COUNT = 6;

    private final CategoryMenuRepository categoryMenuRepository;
    private final Faker faker;

    public CategoryMenuSeeder(CategoryMenuRepository categoryMenuRepository) {
        this.categoryMenuRepository = categoryMenuRepository;
        this.faker = new Faker();
    }

    public List<CategoryMenu> seed() {
        if (categoryMenuRepository.count() > 0) {
            return categoryMenuRepository.findAll();
        }

        List<CategoryMenu> categories = new ArrayList<>();
        for (int index = 0; index < DEFAULT_CATEGORY_COUNT; index++) {
            CategoryMenu categoryMenu = new CategoryMenu();
            categoryMenu.setCategoryName(nextCategoryName(index));
            categoryMenu.setSortOrder(index + 1);
            categoryMenu.setActive(faker.bool().bool());
            categories.add(categoryMenu);
        }

        return categoryMenuRepository.saveAll(categories);
    }

    private String nextCategoryName(int index) {
        return switch (index) {
            case 0 -> faker.options().option("Starters", "Small Plates", "Appetizers");
            case 1 -> faker.options().option("Salads", "Fresh Bowls", "Greens");
            case 2 -> faker.options().option("Main Courses", "Chef Specials", "Entrees");
            case 3 -> faker.options().option("Pasta", "Grill", "Seafood");
            case 4 -> faker.options().option("Desserts", "Sweet Treats", "Bakery");
            default -> faker.options().option("Drinks", "Signature Beverages", "Refreshments");
        };
    }
}
