package com.example.restaurant.model.dao;

import com.example.restaurant.model.entity.Product;

import java.util.List;

public interface ProductDao extends GenericDao<Product> {
    void save(Product product);

    void updateProducts(Product product);

    List<String> getProductsFromOrder(Long orderID);

    Product findByProduct(String product);
}
