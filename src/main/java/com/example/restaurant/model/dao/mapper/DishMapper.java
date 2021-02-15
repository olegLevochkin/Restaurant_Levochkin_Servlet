package com.example.restaurant.model.dao.mapper;

import com.example.restaurant.model.entity.Dish;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DishMapper implements ObjectMapper<Dish> {

    @Override
    public Dish extractFromResultSet(ResultSet rs) throws SQLException {
        Dish dish = new Dish();

        dish.setId(rs.getLong("dish.id"));
        dish.setFileName(rs.getString("dish.file_name"));
        dish.setName(rs.getString("dish.name"));
        dish.setNameUkr(rs.getString("dish.name_ukr"));
        dish.setPrice(BigInteger.valueOf(rs.getInt("dish.price")));

        return dish;
    }

    @Override
    public Dish makeUnique(Map<Long, Dish> cache, Dish entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
