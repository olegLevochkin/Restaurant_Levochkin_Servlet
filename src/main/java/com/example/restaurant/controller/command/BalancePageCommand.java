package com.example.restaurant.controller.command;

import com.example.restaurant.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BalancePageCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(BalancePageCommand.class);
    private final UserService userService;

    public BalancePageCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {

        final HttpSession session = request.getSession();
        String username = "";

        if (userService.getByUsername((String) session.getAttribute("username")) != null) {
            username = (String) session.getAttribute("username");
        }

        request.setAttribute("moneyBalance", userService.getByUsername(username).getBalance());
        log.info("Render balance page");

        return "/WEB-INF/view/addBalance.jsp";
    }
}
