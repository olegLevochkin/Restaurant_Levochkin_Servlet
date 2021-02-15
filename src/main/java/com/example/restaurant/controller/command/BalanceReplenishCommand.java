package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.User;
import com.example.restaurant.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class BalanceReplenishCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(BalanceReplenishCommand.class);
    private final UserService userService;

    public BalanceReplenishCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) throws Exception {
        final HttpSession session = request.getSession();
        String username = "";

        if (userService.getByUsername((String) session.getAttribute("username")) != null) {
            username = (String) session.getAttribute("username");
        }

        User user = userService.getByUsername(username);

        BigDecimal moneyToAdd;
        if (!request.getParameter("moneyToAdd").equals("") && request.getParameter("moneyToAdd").matches("\\d*")) {
            moneyToAdd = BigDecimal.valueOf(Long.parseLong(request.getParameter("moneyToAdd")));
            if (moneyToAdd.compareTo(BigDecimal.ZERO) < 0) {
                request.setAttribute("balanceError", "true");
                return "/WEB-INF/view/addBalance.jsp";
            }
        } else {
            request.setAttribute("balanceError", "true");
            return "/WEB-INF/view/addBalance.jsp";
        }

        BigDecimal newBalance;
        if (user.getBalance() == null) {
            user.setBalance(BigDecimal.ZERO);
        }
        newBalance = user.getBalance();
        newBalance = newBalance.add(moneyToAdd);

        user.setBalance(newBalance);

        userService.saveNewUser(user);
        log.info("Balance has been successfully replenished");

        return "redirect:/addMoney";
    }
}
