package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.OrderDish;
import com.example.restaurant.services.OrderService;
import com.example.restaurant.services.ProductService;
import com.example.restaurant.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderAddedCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(OrderAddedCommand.class);
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public OrderAddedCommand(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
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
        } else {
            return "redirect:/";
        }

        List<String> products = productService.getAllProductsFromOrder(notCompletedOrderId.get());
        Map<String, Integer> enoughtProducts = getProductNeededForOrder(products);
        if (products.stream().distinct().map(product -> {
            try {
                return productService.getByProductName(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull)
                .anyMatch(s -> s.getAmountHave() - enoughtProducts.get(s.getProduct()) < 0)) {
            return "redirect:/order";
        }

        orderDish.setToAdmin(true);
        orderService.updateToAdmin(orderDish);
        log.info("Add dish to order");

        return "redirect:/menu";
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
