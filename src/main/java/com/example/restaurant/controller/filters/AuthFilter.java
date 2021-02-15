package com.example.restaurant.controller.filters;

import com.example.restaurant.model.entity.Role;
import com.example.restaurant.model.entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {

    private final List<String> adminPaths = Arrays.asList(
            "/menu",
            "/addMoney",
            "/addBalance",
            "/user_confirm",
            "/checkOrder",
            "/checkOrder/Confirm",
            "/order",
            "/order/AddToCard",
            "/order/removeD",
            "/adminOrder",
            "/balance",
            "/checkOrderUser/Confirm",
            "/users",
            "/add",
            "/add/addDish",
            "/add/removeDish",
            "/add/addedOrder",
            "/logout");
    private final List<String> authorizedPaths = Arrays.asList(
            "/menu",
            "/addMoney",
            "/addBalance",
            "/checkOrderUser/Confirm",
            "/user_confirm",
            "/checkOrderUser",
            "/order",
            "/order/AddToCard",
            "/order/removeD",
            "/order/addedOrder",
            "/balance",
            "/logout");
    private final List<String> unauthorizedPaths = Arrays.asList(
            "/menu",
            "/login",
            "/registration");
    private final Map<Role, List<String>> allowedPathPatterns = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedPathPatterns.put(Role.USER, authorizedPaths);
        allowedPathPatterns.put(Role.ADMIN, adminPaths);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String uri = request.getRequestURI().replaceFirst(request.getContextPath() + "/app", "");
        User user = (User) session.getAttribute("user");

        if (Objects.isNull(user)) {
            if (unauthorizedPaths.contains(uri)) {
                filterChain.doFilter(request, response);
                return;
            } else {
                response.sendRedirect(request.getContextPath() +
                        request.getServletPath() +
                        "/login");
                return;
            }
        }

        List<String> paths = user.getAuthorities().stream()
                .flatMap(authority -> allowedPathPatterns.get(authority).stream())
                .distinct()
                .collect(Collectors.toList());

        if (paths.contains(uri)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(403);
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
        }

    }

    @Override
    public void destroy() {
    }
}


