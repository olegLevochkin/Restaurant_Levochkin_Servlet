package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.Dish;
import com.example.restaurant.services.DishService;
import com.example.restaurant.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminCheckOrderCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(AdminCheckOrderCommand.class);
    private final DishService dishService;
    private final ProductService productService;

    public AdminCheckOrderCommand(DishService dishService, ProductService productService) {
        this.dishService = dishService;
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {

        final Long ind = Long.valueOf(request.getParameter("orderID"));
        request.setAttribute("orderID", ind);

        Map<Dish, Long> orderClient = new HashMap<>();
        dishService.findByOrderID(ind)
                .stream().distinct().forEach(s -> {
            try {
                orderClient.put(s, dishService
                        .findByOrderID(ind).stream()
                        .filter(x -> x.equals(s)).count());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        request.setAttribute("orderClient", orderClient);

        Map<String, Long> neededProducts = new HashMap<>();
        productService.getAllProductsFromOrder(ind).stream().map(s -> neededProducts.containsKey(s)
                ? neededProducts.put(s, neededProducts.get(s) + 1L)
                : neededProducts.put(s, 1L)).collect(Collectors.toList());

        request.setAttribute("products", neededProducts);
        log.info("Render page to check order for admin");

        return "/WEB-INF/view/checkPage.jsp";
    }
}
