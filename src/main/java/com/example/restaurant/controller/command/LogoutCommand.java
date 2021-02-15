package com.example.restaurant.controller.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        log.info(String.format("Session is over for user: %s", session.getAttribute("username")));
        session.invalidate();

        return "/WEB-INF/view/login.jsp";
    }
}
