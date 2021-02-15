package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.User;
import com.example.restaurant.services.BankTransactionException;
import com.example.restaurant.services.OrderService;
import com.example.restaurant.services.ProductService;
import com.example.restaurant.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MoneyCheckOrderUserConfirmCommand implements Command {
    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;

    public MoneyCheckOrderUserConfirmCommand(UserService userService, OrderService orderService, ProductService productService) {
        this.userService = userService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {
        final Long ind = Long.valueOf(request.getParameter("ind"));

        if (orderService.getByID(Math.toIntExact(ind)).get().isPayed()) {
            return "redirect:/user_confirm";
        }

        List<String> products = productService.getAllProductsFromOrder(ind);

        Map<String, Integer> enoughtProducts = prodNeeded(products);

        List<String> best = enoughtProducts.keySet().stream().filter(s -> enoughtProducts.get(s)
                .equals(enoughtProducts.keySet().stream()
                        .map(enoughtProducts::get).max(Integer::compareTo).get())).collect(Collectors.toList());

        final HttpSession session = request.getSession();
        String username = "";

        if (userService.getByUsername((String) session.getAttribute("username")) != null) {
            username = (String) session.getAttribute("username");
        }

        User user = userService.getByUsername(username);

        user.setProdLikeNow(productService.getByProductName(best.get(0)));

        userService.saveBestProduct(user);

        if (products.stream().distinct().map(product -> {
            try {
                return productService.getByProductName(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull)
                .anyMatch(s -> s.getAmountHave() - enoughtProducts.get(s.getProduct()) < 0)) {
            request.setAttribute("ind", orderService.confirmToUser(userService.getUserIdByUsername(username)));

            request.setAttribute("notEnought", ind);

            return "/WEB-INF/view/PayOrder.jsp";
        }

        productService.getAllProductsFromOrder(ind).stream().map(product -> {
            try {
                return productService.getByProductName(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull)
                .forEach(s -> {
                    s.setAmountHave(s.getAmountHave() - 1);
                    try {
                        productService.updateProducts(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        try {
            userService.payForOrder(orderService.getByID(Math.toIntExact(ind)).get().getPriceAll(), username);
            orderService.payed(ind);
        } catch (BankTransactionException e) {
            request.setAttribute("balanceErrorSum", true);
            return "/WEB-INF/view/addBalance.jsp";
        }

        return "redirect:/menu";
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
