package com.example.restaurant.controller.command;

import com.example.restaurant.services.DishService;
import com.example.restaurant.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class DishCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(DishCommand.class);
    private final DishService dishService;
    private final ProductService productService;

    public DishCommand(DishService dishService, ProductService productService) {
        this.dishService = dishService;
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("property/messages", CommandUtility.getSessionLocale(request));

        request.setAttribute("language", resourceBundle.getLocale().getLanguage());
        request.setAttribute("dishes", dishService.getAllDishes());
        request.setAttribute("products", productService.getAllProducts());
        log.info("Render dishes control page");

        return "/WEB-INF/view/addDish.jsp";
    }
}
