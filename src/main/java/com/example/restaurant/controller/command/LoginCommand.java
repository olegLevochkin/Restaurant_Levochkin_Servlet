package com.example.restaurant.controller.command;

import com.example.restaurant.model.entity.User;
import com.example.restaurant.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginCommand implements Command {

    private final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(LoginCommand.class);

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("property/messages", CommandUtility.getSessionLocale(request));

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (Objects.isNull(username) || Objects.isNull(password)) {
            log.info("Render the login page");
            return "/WEB-INF/view/login.jsp";
        }

        User user;

        try {
            user = userService.getByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message_er", resourceBundle.getString("login.loginErrorMessage"));
            log.info("Invalid username");

            return "/WEB-INF/view/login.jsp";
        }

        if (Objects.isNull(user)) {
            request.setAttribute("message_er", resourceBundle.getString("login.loginErrorMessage"));
            log.info("Invalid username");

            return "/WEB-INF/view/login.jsp";
        }

        if (user.getPassword().equals(DigestUtils.md5Hex(password).toUpperCase())) {
            final HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("balance", user.getBalance());
            session.setAttribute("roles", user.getAuthorities());
            log.info("authorization successful");

            return "redirect:/menu";
        } else {
            request.setAttribute("message_er", resourceBundle.getString("login.loginErrorMessage"));
            log.info("Invalid password");

            return "/WEB-INF/view/login.jsp";
        }
    }
}
