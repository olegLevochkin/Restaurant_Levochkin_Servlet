package com.example.restaurant.model.dao.mapper;

import com.example.restaurant.model.entity.OrderDish;
import com.example.restaurant.model.entity.User;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderMapper implements ObjectMapper<OrderDish> {

    @Override
    public OrderDish extractFromResultSet(ResultSet rs) throws SQLException {
        OrderDish orderDish = new OrderDish();

        orderDish.setId(rs.getLong("order_dish.id"));
        orderDish.setPayed(rs.getBoolean("order_dish.payed"));
        orderDish.setPriceAll(BigInteger.valueOf(rs.getInt("order_dish.price_all")));
        orderDish.setToAdmin(rs.getBoolean("order_dish.to_admin"));
        orderDish.setChecked(rs.getBoolean("order_dish.checked"));

        User newUser = new User();
        newUser.setId(rs.getLong("order_dish.user_id"));
        orderDish.setUser(newUser);

        orderDish.setCompleted(rs.getBoolean("order_dish.completed"));

        return orderDish;
    }

    @Override
    public OrderDish makeUnique(Map<Long, OrderDish> cache, OrderDish entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
