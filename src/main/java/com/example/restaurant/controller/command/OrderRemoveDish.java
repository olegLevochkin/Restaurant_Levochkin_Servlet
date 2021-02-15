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
import java.util.Optional;
import java.util.stream.IntStream;

public class OrderRemoveDish implements Command {
    private static final Logger log = LoggerFactory.getLogger(OrderRemoveDish.class);
    private final UserService userService;
    private final DishService dishService;
    private final OrderService orderService;

    public OrderRemoveDish(UserService userService, DishService dishService, OrderService orderService) {
        this.userService = userService;
        this.dishService = dishService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {
        final HttpSession session = request.getSession();
        String username = "";
        final String dishname = request.getParameter("dish");
        final Dish dish = dishService.findByDishName(dishname);

        if (userService.getByUsername((String) session.getAttribute("username")) != null) {
            username = (String) session.getAttribute("username");
        }

        Optional<Long> notCompletedOrderId = orderService.getUnCompletedForUser(username);
        OrderDish orderDish;

        if (notCompletedOrderId.isPresent()) {
            orderDish = orderService.getByID(Math.toIntExact(notCompletedOrderId.get())).get();
        } else {
            return "redirect:/";
        }

        IntStream.range(0, orderDish.getDishes().size())
                .filter(s -> false).limit(1)
                .forEach(s -> {
                    orderDish.setPriceAll(orderDish.getPriceAll().subtract(orderDish.getDishes()
                            .get(s).getPrice()));

                    orderDish.getDishes().remove(s);
                });

        log.info(String.format("Remove dish: %s", dish.getName()));
        orderService.removeDish(orderDish, dish);

        return "redirect:/order";
    }
}
