package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.Dish;
import com.example.restaurant.model.entity.OrderDish;
import com.example.restaurant.services.DishService;
import com.example.restaurant.services.OrderService;
import com.example.restaurant.services.ProductService;
import com.example.restaurant.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(OrderCommand.class);
    private final UserService userService;
    private final DishService dishService;
    private final ProductService productService;
    private final OrderService orderService;

    public OrderCommand(UserService userService, DishService dishService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.dishService = dishService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {

        final HttpSession session = request.getSession();
        String username = "";

        if (userService.getByUsername((String) session.getAttribute("username")) != null) {
            username = (String) session.getAttribute("username");
        }

        final Optional<Long> notCompletedOrderId = orderService.getUnCompletedForUser(username);
        OrderDish orderDish;

        if (notCompletedOrderId.isPresent()) {
            orderDish = orderService.getByID(Math.toIntExact(notCompletedOrderId.get())).get();
            request.setAttribute("isEmpty", "false");
        } else {
            request.setAttribute("empty", "true");
            return "/WEB-INF/view/order.jsp";
        }

        Map<Dish, Long> orderClient = new HashMap<>();
        dishService.findByOrderID(orderDish.getId())
                .stream().distinct().forEach(dish -> {
            try {
                orderClient.put(dish, dishService
                        .findByOrderID(orderDish.getId()).stream()
                        .filter(dishName -> dishName.equals(dish)).count());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        request.setAttribute("map", orderClient);

        BigInteger sum = BigInteger.ZERO;
        for (Dish dish : dishService.findByOrderID(orderDish.getId())) {
            BigInteger intValue = dish.getPrice();
            sum.add(intValue);
        }
        Optional<OrderDish> orderDish1 = orderService.getByID(Math.toIntExact(orderDish.getId()));
        request.setAttribute("amount", orderDish1.get().getPriceAll());

        List<String> products = productService.getAllProductsFromOrder(orderDish.getId());
        Map<String, Integer> enoughtProducts = getProductNeededForOrder(products);

        if (products.stream().distinct().map(product -> {
            try {
                return productService.getByProductName(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull)
                .anyMatch(s -> (s.getAmountHave() - enoughtProducts.get(s.getProduct())) < 0)) {
            request.setAttribute("notEnought", "we dont have enough products");
        }
        log.info("Render information about order");

        return "/WEB-INF/view/order.jsp";
    }

    private Map<String, Integer> getProductNeededForOrder(List<String> products) {
        Map<String, Integer> enoughtProducts = new HashMap<>();
        products
                .stream()
                .map(product -> {
                    try {
                        return productService.getByProductName(product);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }).filter(Objects::nonNull)
                .map(s -> enoughtProducts.containsKey(s.getProduct())
                        ? enoughtProducts.put(s.getProduct(), enoughtProducts.get(s.getProduct()) + 1)
                        : enoughtProducts.put(s.getProduct(), 1)).collect(Collectors.toList());

        return enoughtProducts;
    }
}
