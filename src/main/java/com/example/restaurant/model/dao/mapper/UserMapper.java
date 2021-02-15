package com.example.restaurant.model.dao.mapper;

import com.example.restaurant.model.entity.Role;
import com.example.restaurant.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {

        User result = new User();

        result.setId(rs.getLong("users.id"));
        result.setFirstName(rs.getString("users.first_name"));
        result.setLastName(rs.getString("users.last_name"));
        result.setUsername(rs.getString("users.username"));
        result.setPassword(rs.getString("users.password"));
        result.setBalance(rs.getBigDecimal("users.balance"));
        if (!result.hasAuthority(Role.USER))
            result.addAuthority(Role.USER);

        return result;
    }

    @Override
    public User makeUnique(Map<Long, User> cache, User entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}

