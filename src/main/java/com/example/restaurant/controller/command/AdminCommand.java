package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.Product;
import com.example.restaurant.services.OrderService;
import com.example.restaurant.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class AdminCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(AdminCommand.class);
    private final OrderService orderService;
    private final ProductService productService;

    public AdminCommand(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        try {
            request.setAttribute("orderID", orderService.confirmToAdmin());
            request.setAttribute("products", productService.getAllProducts());
            if (productService.getAllProducts().stream().anyMatch(s -> s.getAmountHave() < s.getMinAmount())) {
                return replenish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Render information for admin ");

        return "/WEB-INF/view/adminOrder.jsp";
    }

    public String replenish() {
        Set<Product> products = new HashSet<>();
        try {
            products = productService.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        products.forEach(product -> {
            product.setAmountHave(product.getAmountHave()
                    + product.getProductInBox() * ((product.getMaxAmount() - product.getAmountHave()) / product.getProductInBox()));

            try {
                productService.saveProduct(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("Product replenishment was successful ");
        });

        return "redirect:/adminOrder";
    }
}
