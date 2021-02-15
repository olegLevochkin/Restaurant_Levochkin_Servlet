package com.example.restaurant.model.dao.mapper;

import com.example.restaurant.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ProductMapper implements ObjectMapper<Product> {

    @Override
    public Product extractFromResultSet(ResultSet rs) throws SQLException {
        Product result = new Product();

        result.setId(rs.getLong("product.id"));
        result.setProduct(rs.getString("product.product"));
        result.setAmountHave(rs.getInt("product.amount_have"));
        result.setMaxAmount(rs.getInt("product.max_Amount"));
        result.setMinAmount(rs.getInt("product.min_Amount"));
        result.setProductInBox(rs.getInt("product.product_in_box"));

        return result;
    }

    @Override
    public Product makeUnique(Map<Long, Product> cache, Product entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
