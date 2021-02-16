package com.example.restaurant.controller.command;

import com.example.restaurant.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class AdminConfirmCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(AdminConfirmCommand.class);
    private final OrderService orderService;

    public AdminConfirmCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {
        final Long ind = Long.valueOf(request.getParameter("orderID"));
        orderService.confirm(ind);
        log.info("Admin confirm order");

        return "redirect:/menu";
    }
}
