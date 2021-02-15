package com.example.restaurant.services;

import com.example.restaurant.model.dao.DaoFactory;
import com.example.restaurant.model.dao.ProductDao;
import com.example.restaurant.model.dao.imp.JDBCDaoFactory;
import com.example.restaurant.model.entity.Product;

import java.util.List;
import java.util.Set;

public class ProductService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public ProductService() {
    }

    public Product getByProductName(String product) throws Exception {

        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findByProduct(product);
        }
    }

    public Set<Product> getAllProducts() throws Exception {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findAll();
        }
    }

    public List<String> getAllProductsFromOrder(Long orderID) throws Exception {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.getProductsFromOrder(orderID);
        }
    }

    public void saveProduct(Product product) throws Exception {
        try (ProductDao dao = daoFactory.createProductDao()) {
            dao.save(product);
        }
    }

    public void updateProducts(Product product) throws Exception {
        try (ProductDao dao = daoFactory.createProductDao()) {
            dao.updateProducts(product);
        }
    }

    public Product findByProductName(String product) throws Exception {
        try (ProductDao dao = daoFactory.createProductDao()) {
            return dao.findByProduct(product);
        }
    }
}
