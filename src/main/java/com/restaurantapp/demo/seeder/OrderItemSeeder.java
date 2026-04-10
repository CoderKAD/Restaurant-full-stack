package com.restaurantapp.demo.seeder;

import com.restaurantapp.demo.entity.MenuItem;
import com.restaurantapp.demo.entity.Order;
import com.restaurantapp.demo.entity.OrderItem;
import com.restaurantapp.demo.repository.OrderItemRepository;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class OrderItemSeeder {

    private final OrderItemRepository orderItemRepository;
    private final Faker faker;

    public OrderItemSeeder(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
        this.faker = new Faker();
    }

    public List<OrderItem> seed(List<Order> orders, List<MenuItem> menuItems) {
        if (orderItemRepository.count() > 0) {
            return orderItemRepository.findAll();
        }

        if (orders.isEmpty() || menuItems.isEmpty()) {
            return Collections.emptyList();
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (int index = 0; index < orders.size(); index++) {
            Order order = orders.get(index);
            int itemCount = faker.number().numberBetween(1, 4);

            for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setMenuItem(menuItems.get((index + itemIndex) % menuItems.size()));
                orderItem.setQuantity(faker.number().numberBetween(1, 5));
                orderItem.setNotes(faker.options().option(
                        "No onions",
                        "Extra spicy",
                        "Serve immediately",
                        "Pack separately",
                        "Chef recommendation"
                ));
                orderItems.add(orderItem);
            }
        }

        return orderItemRepository.saveAll(orderItems);
    }
}
