package com.example.restaurant.controller.command;

import com.example.restaurant.services.OrderService;
import com.example.restaurant.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PayPageCommand implements Command {
    private final UserService userService;
    private final OrderService orderService;

    public PayPageCommand(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {

        final HttpSession session = request.getSession();
        String username = "";

        if (userService.getByUsername((String) session.getAttribute("username")) != null) {
            username = (String) session.getAttribute("username");
        }
        request.setAttribute("ind", orderService.confirmToUser(userService.getUserIdByUsername(username)));

        return "/WEB-INF/view/PayOrder.jsp";
    }
}
