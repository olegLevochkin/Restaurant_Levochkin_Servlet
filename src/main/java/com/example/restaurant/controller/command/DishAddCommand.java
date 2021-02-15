package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.Dish;
import com.example.restaurant.model.entity.Product;
import com.example.restaurant.services.DishService;
import com.example.restaurant.services.ProductService;
import org.eclipse.jetty.server.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

@MultipartConfig
public class DishAddCommand implements Command {

    private DishService dishService;

    private ProductService productService;

    private ResourceBundle resourceBundle;

    private static final Logger log = LoggerFactory.getLogger(DishAddCommand.class);

    private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement("C:/Users/olegl/IdeaProjects/Restaurant_Levochkin_Servlet/src/main/webapp/images");

    public DishAddCommand(DishService dishService, ProductService productService) {
        this.dishService = dishService;
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {
        resourceBundle = ResourceBundle.getBundle("property/messages", CommandUtility.getSessionLocale(request));
        String contentType = request.getContentType();

        String name = "";
        String nameUkr = "";
        String price = "";
        String src = "";

        List<Product> productList = new LinkedList<>();

        if (contentType != null && contentType.startsWith("multipart/")) {
            request.setAttribute(Request.MULTIPART_CONFIG_ELEMENT, MULTI_PART_CONFIG);
            for (Part part : request.getParts()) {
                switch (part.getName()) {
                    case "name": {
                        InputStream inputStream = part.getInputStream();
                        InputStreamReader isr = new InputStreamReader(inputStream);
                        name = new BufferedReader(isr)
                                .lines()
                                .collect(Collectors.joining("\n"));
                        break;
                    }
                    case "nameUkr": {
                        InputStream inputStream = part.getInputStream();
                        InputStreamReader isr = new InputStreamReader(inputStream);
                        nameUkr = new BufferedReader(isr)
                                .lines()
                                .collect(Collectors.joining("\n"));
                        break;
                    }
                    case "price": {
                        InputStream inputStream = part.getInputStream();
                        InputStreamReader isr = new InputStreamReader(inputStream);
                        price = new BufferedReader(isr)
                                .lines()
                                .collect(Collectors.joining("\n"));
                        break;
                    }
                    case "Potato":
                    case "Tomato":
                    case "Carrot":
                    case "Cabbage":
                    case "Pepper":
                    case "Egg":
                    case "Meat": {
                        InputStream inputStream = part.getInputStream();
                        InputStreamReader isr = new InputStreamReader(inputStream);
                        String productName = new BufferedReader(isr)
                                .lines()
                                .collect(Collectors.joining("\n"));
                        productList.add(productService.findByProductName(productName));
                        break;
                    }
                    default:
                        src = UUID.randomUUID().toString() + part.getSubmittedFileName();
                        part.write(src);
                        break;
                }
            }
        }
        Dish newDish = new Dish();
        newDish.setNameUkr(nameUkr);
        newDish.setName(name);
        newDish.setPrice(new BigInteger(price));
        newDish.setFileName(src);
        newDish.setProductsForDish(productList);

        log.info(String.format("Save new dish: %s", newDish));
        dishService.saveDish(newDish);

        return "redirect:/add";
    }
}
