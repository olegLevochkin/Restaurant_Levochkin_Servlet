package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.Dish;
import com.example.restaurant.services.DishService;
import com.example.restaurant.services.OrderService;
import com.example.restaurant.services.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MoneyCheckOrderUserCommand implements Command {
    private final DishService dishService;
    private final OrderService orderService;
    private final ProductService productService;

    public MoneyCheckOrderUserCommand(DishService dishService, OrderService orderService, ProductService productService) {
        this.dishService = dishService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {
        final Long ind = Long.valueOf(request.getParameter("ind"));
        request.setAttribute("index", ind);
        Map<Dish, Long> orderClient = new HashMap<>();

        dishService.findByOrderIDToUSer(ind)
                .stream().distinct().forEach(s -> {
            try {
                orderClient.put(s, dishService
                        .findByOrderIDToUSer(ind).stream()
                        .filter(x -> x.equals(s)).count());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        List<String> products = productService.getAllProductsFromOrder(ind);

        Map<String, Integer> enoughtProducts = prodNeeded(products);

        if (products.stream().distinct().map(product -> {
            try {
                return productService.getByProductName(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull)
                .anyMatch(s -> s.getAmountHave() - enoughtProducts.get(s.getProduct()) < 0)) {
            request.setAttribute("notEnought", ind);
        }

        request.setAttribute("order", orderClient);
        request.setAttribute("price", orderService.getByID(Math.toIntExact(ind)).get().getPriceAll());

        return "/WEB-INF/view/checkPageUser.jsp";
    }

    private Map<String, Integer> prodNeeded(List<String> products) {
        Map<String, Integer> enoughtProducts = new HashMap<>();
        products
                .stream()
                .map(product1 -> {
                    try {
                        return productService.getByProductName(product1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }).filter(Objects::nonNull)
                .map(product -> enoughtProducts.containsKey(product.getProduct())
                        ? enoughtProducts.put(product.getProduct(), enoughtProducts.get(product.getProduct()) + 1)
                        : enoughtProducts.put(product.getProduct(), 1)).collect(Collectors.toSet());

        return enoughtProducts;
    }
}
