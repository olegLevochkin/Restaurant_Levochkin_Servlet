package com.example.restaurant.controller;

import com.example.restaurant.controller.command.AdminCheckOrderCommand;
import com.example.restaurant.controller.command.AdminCommand;
import com.example.restaurant.controller.command.AdminConfirmCommand;
import com.example.restaurant.controller.command.Command;
import com.example.restaurant.controller.command.DishAddCommand;
import com.example.restaurant.controller.command.DishCommand;
import com.example.restaurant.controller.command.DishRemoveCommand;
import com.example.restaurant.controller.command.MenuCommand;
import com.example.restaurant.controller.command.LoginCommand;
import com.example.restaurant.controller.command.LogoutCommand;
import com.example.restaurant.controller.command.BalancePageCommand;
import com.example.restaurant.controller.command.BalanceReplenishCommand;
import com.example.restaurant.controller.command.MoneyCheckOrderUserCommand;
import com.example.restaurant.controller.command.MoneyCheckOrderUserConfirmCommand;
import com.example.restaurant.controller.command.PayPageCommand;
import com.example.restaurant.controller.command.OrderAddToCard;
import com.example.restaurant.controller.command.OrderAddedCommand;
import com.example.restaurant.controller.command.OrderCommand;
import com.example.restaurant.controller.command.OrderRemoveDish;
import com.example.restaurant.controller.command.RegistrationCommand;
import com.example.restaurant.controller.command.UsersCommand;
import com.example.restaurant.services.DishService;
import com.example.restaurant.services.OrderService;
import com.example.restaurant.services.ProductService;
import com.example.restaurant.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MainServlet extends HttpServlet {

    private final Map<String, Command> commands = new HashMap<>();
    private static final Logger log = LogManager.getLogger();

    private final UserService userService = new UserService();
    private final DishService dishService = new DishService();
    private final OrderService orderService = new OrderService();
    private final ProductService productService = new ProductService();

    public void init(ServletConfig servletConfig) {

        commands.put("/logout", new LogoutCommand());
        commands.put("/login", new LoginCommand(userService));
        commands.put("/registration", new RegistrationCommand(userService));
        commands.put("/add", new DishCommand( dishService, productService));
        commands.put("/add/addDish", new DishAddCommand(dishService, productService));
        commands.put("/add/removeDish", new DishRemoveCommand(dishService));
        commands.put("/menu", new MenuCommand(userService, dishService));
        commands.put("/users", new UsersCommand(userService));
        commands.put("/order", new OrderCommand(userService, dishService, productService, orderService));
        commands.put("/order/AddToCard", new OrderAddToCard(userService, dishService, orderService));
        commands.put("/order/removeD", new OrderRemoveDish(userService, dishService, orderService));
        commands.put("/order/addedOrder", new OrderAddedCommand(userService, productService, orderService));
        commands.put("/checkOrder", new AdminCheckOrderCommand(dishService, productService));
        commands.put("/user_confirm", new PayPageCommand(userService, orderService));
        commands.put("/checkOrderUser", new MoneyCheckOrderUserCommand(dishService, orderService, productService));
        commands.put("/checkOrderUser/Confirm", new MoneyCheckOrderUserConfirmCommand(userService, orderService, productService));
        commands.put("/checkOrder/Confirm", new AdminConfirmCommand(orderService));
        commands.put("/addMoney", new BalancePageCommand(userService));
        commands.put("/addBalance", new BalanceReplenishCommand(userService));
        commands.put("/adminOrder", new AdminCommand(orderService, productService));

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().replaceFirst(request.getContextPath() + "/app", "");
        Command command = commands.getOrDefault(path,
                (r) -> "/WEB-INF/view/menu.jsp");

        log.info("Current command: " + command.getClass().getSimpleName());
        String page = null;
        try {
            page = command.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Objects.requireNonNull(page).contains("redirect")) {
            response.sendRedirect(request.getContextPath() + request.getServletPath() + page.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

}
