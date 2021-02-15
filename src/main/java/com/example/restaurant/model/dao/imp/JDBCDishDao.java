package com.example.restaurant.model.dao.imp;

import com.example.restaurant.exception.DeleteDependentException;
import com.example.restaurant.model.dao.DishDao;
import com.example.restaurant.model.dao.mapper.DishMapper;
import com.example.restaurant.model.dao.mapper.ProductMapper;
import com.example.restaurant.model.entity.Dish;
import com.example.restaurant.model.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class JDBCDishDao implements DishDao {
    private final Connection connection;

    public JDBCDishDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Dish entity) {

    }

    @Override
    public Optional<Dish> findById(int id) {
        return Optional.empty();
    }

    private static final String FIND_ALL_QUERY = "SELECT" +
            " dish.id AS \"dish.id\"," +
            " dish.file_name AS \"dish.file_name\"," +
            " dish.name AS \"dish.name\"," +
            " dish.name_ukr AS \"dish.name_ukr\"," +
            " dish.price AS \"dish.price\"" +
            " FROM dish" +
            " ORDER BY dish.id";

    @Override
    public Set<Dish> findAll() {
        LinkedHashSet<Dish> result;

        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_QUERY)) {

            ResultSet rs = ps.executeQuery();
            LinkedHashMap<Long, Dish> users = extractFromResultSet(rs);
            result = new LinkedHashSet<>(users.values());

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Dish entity) {

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

    private static final String CREATE_DISH = "INSERT INTO dish (file_name," +
            " name, name_ukr, price ) VALUES (?, ?, ?, ?)";

    private static final String INSERT_INTO_PRODUCTS_FOR_DISH_QUERY = "INSERT INTO dish_products_for_dish (dishes_id, products_for_dish_id) VALUES (?, ?)";

    public void save(Dish dish) {

        try (PreparedStatement ps = connection.prepareStatement
                (CREATE_DISH, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, dish.getFileName());
            ps.setString(2, dish.getName());
            ps.setString(3, dish.getNameUkr());
            ps.setInt(4, dish.getPrice().intValue());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Long id = rs.getLong(1);
                dish.setId(id);
            }
            List<Product> productsList = dish.getProductsForDish();
            try (PreparedStatement aps = connection.prepareStatement(INSERT_INTO_PRODUCTS_FOR_DISH_QUERY)) {
                for (Product product : productsList) {
                    aps.setLong(1, dish.getId());
                    aps.setLong(2, product.getId());
                    aps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DELETE_DISH = "DELETE FROM dish WHERE dish.id = ?";

    private static final String DELETE_PRODUCTS_TO_DISH = "DELETE FROM dish_products_for_dish WHERE dishes_id = ?";

    public void deleteById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_DISH)) {

            try (PreparedStatement aps = connection.prepareStatement(DELETE_PRODUCTS_TO_DISH)) {
                aps.setLong(1, id);
                aps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String QUERY_BY_ORDER = "select  dish.id AS \"dish.id\",\n" +
            "dish.file_name AS \"dish.file_name\", \n" +
            "dish.name AS \"dish.name\",\n" +
            "dish.price AS \"dish.price\",\n" +
            "dish.name_ukr AS \"dish.name_ukr\"\n" +
            "from dish, \n" +
            "order_dish_dishes, \n" +
            "order_dish\n" +
            "where dish.id = order_dish_dishes.dishes_id \n" +
            "AND order_dish_dishes.orders_with_dish_id = order_dish.id \n" +
            "AND order_dish.checked=0 \n" +
            "AND order_dish.id=?";

    public List<Dish> findByOrder(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(QUERY_BY_ORDER)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Long, Dish> dishes = extractFromResultSet(rs);

            List<Dish> result = new ArrayList<>();

            for (Map.Entry<Long, Dish> entry : dishes.entrySet()) {
                result.add(entry.getValue());
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Dish> findAllByOrderID(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(QUERY_BY_ORDER)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Long, Dish> dishes = extractFromResultSet(rs);

            List<Dish> result = new ArrayList<>();

            for (Map.Entry<Long, Dish> entry : dishes.entrySet()) {
                result.add(entry.getValue());
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static final String QUERY_BY_ORDER_TO_USER = "select  dish.id AS \"dish.id\",\n" +
            "dish.file_name AS \"dish.file_name\",\n" +
            "dish.name AS \"dish.name\",\n" +
            "dish.price AS \"dish.price\",\n" +
            "dish.name_ukr AS \"dish.name_ukr\"\n" +
            "from dish, \n" +
            "order_dish_dishes,\n" +
            "order_dish\n" +
            "where dish.id = order_dish_dishes.dishes_id\n" +
            "AND order_dish_dishes.orders_with_dish_id = order_dish.id \n" +
            "AND order_dish.checked=1\n" +
            "AND order_dish.id=?";

    public List<Dish> findByOrderToUser(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(QUERY_BY_ORDER_TO_USER)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Long, Dish> dishes = extractFromResultSet(rs);

            List<Dish> result = new ArrayList<>();

            for (Map.Entry<Long, Dish> entry : dishes.entrySet()) {
                result.add(entry.getValue());
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private static final String FIND_BY_DISHNAME_QUERY = "SELECT dish.id AS \"dish.id\",\n" +
            "dish.file_name AS \"dish.file_name\",\n" +
            "dish.name AS \"dish.name\",\n" +
            "dish.name_ukr AS \"dish.name_ukr\",\n" +
            "dish.price AS \"dish.price\"\n" +
            "FROM dish\n" +
            "WHERE dish.name=?";

    public Dish findByName(String name) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_DISHNAME_QUERY)) {
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            Map<Long, Dish> dishes = extractFromResultSet(rs);

            for (Map.Entry<Long, Dish> entry : dishes.entrySet()) {
                if (entry.getValue() != null) {
                    return entry.getValue();
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String EXTRACT_PRODUCTS_QUERY = "SELECT dish_products_for_dish.dishes_id AS \"dish_products_for_dish.dishes_id\",\n" +
            "product.id AS \"product.id\",\n" +
            "product.amount_have AS \"product.amount_have\",\n" +
            "product.max_amount AS \"product.max_amount\",\n" +
            "product.min_amount AS \"product.min_amount\",\n" +
            "product.product AS \"product.product\",\n" +
            "product.product_in_box AS \"product.product_in_box\"\n" +
            "FROM dish_products_for_dish LEFT JOIN product \n" +
            "ON dish_products_for_dish.products_for_dish_id = product.id\n" +
            "WHERE dish_products_for_dish.dishes_id=?";

    private LinkedHashMap<Long, Dish> extractFromResultSet(ResultSet rss) throws SQLException {

        DishMapper dishMapper = new DishMapper();
        ProductMapper productMapper = new ProductMapper();
        LinkedHashMap<Long, Dish> dishes = new LinkedHashMap<>();

        Long counter = 0L;

        while (rss.next()) {
            Dish dish = dishMapper.extractFromResultSet(rss);
            dishes.put(counter, dish);
            counter++;
        }

        for (Dish e : dishes.values()) {

            try (PreparedStatement ps = connection.prepareStatement(
                    EXTRACT_PRODUCTS_QUERY)) {

                ps.setLong(1, e.getId());
                ResultSet rsa = ps.executeQuery();
                List<Product> productList = new ArrayList<>(Collections.emptyList());

                while (rsa.next()) {
                    Product product = productMapper.extractFromResultSet(rsa);
                    productList.add(product);
                }

                e.setProductsForDish(productList);

            }

        }

        return dishes;
    }
}
