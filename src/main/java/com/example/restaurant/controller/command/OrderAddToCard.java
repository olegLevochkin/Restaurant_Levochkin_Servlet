package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.Dish;
import com.example.restaurant.model.entity.OrderDish;
import com.example.restaurant.services.DishService;
import com.example.restaurant.services.OrderService;
import com.example.restaurant.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderAddToCard implements Command {
    private static final Logger log = LoggerFactory.getLogger(OrderAddToCard.class);
    private final UserService userService;
    private final DishService dishService;
    private final OrderService orderService;

    public OrderAddToCard(UserService userService, DishService dishService, OrderService orderService) {
        this.userService = userService;
        this.dishService = dishService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {

        final HttpSession session = request.getSession();
        String username = "";
        final String dish = request.getParameter("dish");

        if (userService.getByUsername((String) session.getAttribute("username")) != null) {
            username = (String) session.getAttribute("username");
            log.info(String.format("Find user: %s", session.getAttribute("username")));
        }

        final Optional<Long> notCompletedOrderId = orderService.getUnCompletedForUser(username);
        OrderDish orderDish;

        if (notCompletedOrderId.isPresent()) {
            orderDish = orderService.getByID(Math.toIntExact(notCompletedOrderId.get())).get();
            List<Dish> dishes = Collections.singletonList(dishService.findByDishName(dish));
            orderDish.setDishes(dishes);
        } else {
            orderDish = new OrderDish();
            orderDish.setDishes(Collections.singletonList(dishService.findByDishName(dish)));
            orderDish.setPayed(false);
            orderDish.setChecked(false);
            orderDish.setToAdmin(false);
            orderDish.setCompleted(false);
            orderDish.setUser(userService.getByUsername(username));
        }

        BigInteger sum = BigInteger.ZERO;
        for (Dish dish1 : orderDish.getDishes()) {
            BigInteger price = dish1.getPrice();
            sum = sum.add(price);
        }
        log.info(String.format("Total sum of order: %s", sum));

        orderDish.setPriceAll(sum);
        if (notCompletedOrderId.isPresent()) {
            orderService.addDishINOrder(orderDish);
        } else {
            orderService.saveOrder(orderDish);
        }
        log.info(String.format("Save order with id: %s", orderDish.getId()));

        return "redirect:/menu";
    }
}
