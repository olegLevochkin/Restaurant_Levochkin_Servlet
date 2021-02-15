package com.example.restaurant.model.dao.imp;

import com.example.restaurant.exception.DeleteDependentException;
import com.example.restaurant.model.dao.ProductDao;
import com.example.restaurant.model.dao.mapper.ProductMapper;
import com.example.restaurant.model.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class JDBCProductDao implements ProductDao {

    private final Connection connection;

    JDBCProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Product entity) {

    }

    @Override
    public Optional<Product> findById(int id) {
        return Optional.empty();
    }

    private static final String FIND_ALL_QUERY = "SELECT product.id AS \"product.id\",\n" +
            "product.product AS \"product.product\",\n" +
            "product.amount_have AS \"product.amount_have\",\n" +
            "product.max_amount AS \"product.max_amount\",\n" +
            "product.min_amount AS \"product.min_amount\",\n" +
            "product.product_in_box AS \"product.product_in_box\"\n" +
            "FROM product";

    @Override
    public Set<Product> findAll() {
        LinkedHashSet<Product> result;

        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_QUERY)) {

            ResultSet rs = ps.executeQuery();
            LinkedHashMap<Long, Product> products = extractFromResultSet(rs);
            result = new LinkedHashSet<>(products.values());

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product entity) {

    }

    @Override
    public void delete(int id) throws DeleteDependentException {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String FIND_BY_PRODUCT_NAME = "SELECT product.id AS \"product.id\", \n" +
            "product.amount_have AS \"product.amount_have\",\n" +
            "product.max_amount AS \"product.max_amount\",\n" +
            "product.min_amount AS \"product.min_amount\",\n" +
            "product.product AS \"product.product\",\n" +
            "product.product_in_box AS \"product.product_in_box\"\n" +
            "FROM product WHERE product.product=?";


    public Product findByProduct(String product) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_PRODUCT_NAME)) {
            ps.setString(1, product);
            ResultSet rs = ps.executeQuery();

            Map<Long, Product> products = extractFromResultSet(rs);
            for (Map.Entry<Long, Product> entry : products.entrySet()) {
                if (entry.getValue() != null) {
                    return entry.getValue();
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String FIND_BY_ID = "SELECT product.product AS \"product.product\"\n" +
            "FROM dish,\n" +
            "order_dish,\n" +
            "dish_products_for_dish,\n" +
            "product,\n" +
            "order_dish_dishes \n" +
            "WHERE order_dish.id = ?\n" +
            "AND dish_products_for_dish.dishes_id = dish.id \n" +
            "AND product.id = dish_products_for_dish.products_for_dish_id\n" +
            "AND order_dish_dishes.dishes_id = dish.id\n" +
            "AND order_dish_dishes.orders_with_dish_id = ?";

    public List<String> getProductsFromOrder(Long id) {

        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setLong(1, id);
            ps.setLong(2, id);

            ResultSet resultSet = ps.executeQuery();

            List<String> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(resultSet.getString("product.product"));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static final String CREATE_PRODUCT = "INSERT INTO product (amount_have," +
            " max_amount, min_amount, product, product_in_box ) VALUES (?, ?, ?, ?, ?)";

    public void save(Product entity) {

        try (PreparedStatement ps = connection.prepareStatement
                (CREATE_PRODUCT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getAmountHave());
            ps.setInt(2, entity.getMaxAmount());
            ps.setInt(3, entity.getMinAmount());
            ps.setString(4, entity.getProduct());
            ps.setInt(5, entity.getProductInBox());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Long id = rs.getLong(1);
                entity.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String UPDATE_PRODUCT = "UPDATE product SET amount_have = ? WHERE product.id = ?";

    public void updateProducts(Product entity) {

        try (PreparedStatement ps = connection.prepareStatement
                (UPDATE_PRODUCT)) {
            ps.setInt(1, entity.getAmountHave());
            ps.setLong(2, entity.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private LinkedHashMap<Long, Product> extractFromResultSet(ResultSet rss) throws SQLException {

        ProductMapper productMapper = new ProductMapper();
        LinkedHashMap<Long, Product> products = new LinkedHashMap<>();

        while (rss.next()) {
            Product product = productMapper.extractFromResultSet(rss);
            productMapper.makeUnique(products, product);
        }

        return products;
    }
}
