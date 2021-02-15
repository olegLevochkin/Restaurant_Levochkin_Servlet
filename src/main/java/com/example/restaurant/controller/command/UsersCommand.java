package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.User;
import com.example.restaurant.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class UsersCommand implements Command {
    private final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(LoginCommand.class);

    public UsersCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        Set<User> users = null;
        try {
            users = userService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Print all users");

        request.setAttribute("users", users);

        return "/WEB-INF/view/allUsers.jsp";
    }
}
