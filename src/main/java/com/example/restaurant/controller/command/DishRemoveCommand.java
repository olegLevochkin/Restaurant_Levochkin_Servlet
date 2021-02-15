package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.Dish;
import com.example.restaurant.services.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class DishRemoveCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(DishRemoveCommand.class);
    private final DishService dishService;

    public DishRemoveCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {
        String[] disheNames = request.getParameterValues("dishesToRemove");
        for (String dishName : disheNames) {
            log.info(String.format("Remove dish: %s", dishName));
            Dish dishToRemove = dishService.findByDishName(dishName);
            dishService.deleteByID(dishToRemove.getId());
        }

        return "redirect:/add";
    }
}
