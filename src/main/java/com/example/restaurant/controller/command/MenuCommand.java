package com.example.restaurant.controller.command;

import com.example.restaurant.services.DishService;
import com.example.restaurant.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MenuCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(LoginCommand.class);
    private final UserService userService;
    private final DishService dishService;

    public MenuCommand(UserService userService, DishService dishService) {
        this.userService = userService;
        this.dishService = dishService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {

        final HttpSession session = request.getSession();
        String username = "";
        if (session.getAttribute("username") != null) {
            username = session.getAttribute("username").toString();
        }

        if (!username.equals("") && userService.getByUsername(username) != null) {
            log.info("Render the menu page basic");

            request.setAttribute("isAuthorize", userService.getByUsername(username).getAuthorities().size());
            request.setAttribute("moneyBalance", userService.getByUsername((String) session.getAttribute("username")).getBalance());
        } else {
            request.setAttribute("isAuthorize", 0);
        }

        request.setAttribute("dishes", dishService.getAllDishes());
        log.info("Render the menu page for authorized user");

        return "/WEB-INF/view/menu.jsp";
    }
}
