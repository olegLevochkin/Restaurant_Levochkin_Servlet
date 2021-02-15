package com.example.restaurant.controller.command;

import com.example.restaurant.controller.validators.NameValidator;
import com.example.restaurant.controller.validators.PasswordValidator;
import com.example.restaurant.controller.validators.Result;
import com.example.restaurant.model.entity.User;
import com.example.restaurant.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class RegistrationCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(RegistrationCommand.class);
    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("property/messages",
                CommandUtility.getSessionLocale(request));

        NameValidator nameValidator = new NameValidator(6, 30,
                resourceBundle.getString("valid.username"));

        final String firstName = request.getParameter("firstName");
        final String lastName = request.getParameter("lastName");
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");

        if (!(firstName != null && lastName != null && username != null && password != null)) {
            log.info("Render the registration page");

            return "/WEB-INF/view/register.jsp";
        }

        Result result = nameValidator.validate(username);

        if (!result.isOk()) {
            log.info("The entered username is invalid");
            request.setAttribute("valid_Error", result.getMessage());

            return handleRegistrationError(request, firstName, lastName,
                    username, password);
        }

        try {
            if (userService.isDuplicateUsername(username)) {
                log.info("The entered username is a duplicate");
                request.setAttribute("valid_Error", resourceBundle.getString("valid.username.not_unique"));

                return handleRegistrationError(request, firstName, lastName, username, password);
            }
        } catch (Exception e) {
            log.info("Error checking username for duplicate");
            e.printStackTrace();
        }

        nameValidator = new NameValidator(2, 30, resourceBundle.getString("valid.first"));
        result = nameValidator.validate(firstName);

        if (!result.isOk()) {
            log.info("The entered first name is invalid");
            request.setAttribute("valid_Error", result.getMessage());

            return handleRegistrationError(request, firstName, lastName,
                    username, password);
        }

        nameValidator = new NameValidator(2, 30,
                resourceBundle.getString("valid.last"));
        result = nameValidator.validate(lastName);

        if (!result.isOk()) {
            log.info("The entered last name is invalid");
            request.setAttribute("valid_Error", result.getMessage());

            return handleRegistrationError(request, firstName, lastName,
                    username, password);
        }

        PasswordValidator passwordValidator = new PasswordValidator(6, 30, "Error");
        result = passwordValidator.validate(password);

        if (!result.isOk()) {
            log.info("The entered password is invalid");
            request.setAttribute("valid_Error", result.getMessage());

            return handleRegistrationError(request, firstName, lastName,
                    username, password);
        }

        String hashedPassword = DigestUtils.md5Hex(password).toUpperCase();

        final User user = new User(firstName, lastName, username, hashedPassword);

        try {
            userService.registerUser(user);
            log.info("Save new user");
        } catch (Exception e) {
            log.info("Error while saving user");
            e.printStackTrace();
        }

        return "redirect:/login";
    }

    private String handleRegistrationError(HttpServletRequest request,
                                           String firstName, String lastName,
                                           String username, String password) {
        request.setAttribute("first_name", firstName);
        request.setAttribute("last_name", lastName);
        request.setAttribute("username", username);
        request.setAttribute("password", password);

        return "/WEB-INF/view/register.jsp";
    }

}

