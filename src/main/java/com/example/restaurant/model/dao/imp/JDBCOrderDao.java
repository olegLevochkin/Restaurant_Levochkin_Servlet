package com.example.restaurant.model.dao.imp;

import com.example.restaurant.exception.DeleteDependentException;
import com.example.restaurant.model.dao.OrderDao;
import com.example.restaurant.model.dao.mapper.OrderMapper;
import com.example.restaurant.model.entity.Dish;
import com.example.restaurant.model.entity.OrderDish;
import com.example.restaurant.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class JDBCOrderDao implements OrderDao {

    private final Connection connection;

    JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(OrderDish entity) {

    }

    private static final String FIND_BY_ID_QUERY = "SELECT order_dish.id AS \"order_dish.id\",\n" +
            "order_dish.checked AS \"order_dish.checked\",\n" +
            "order_dish.payed AS \"order_dish.payed\",\n" +
            "order_dish.price_all AS \"order_dish.price_all\",\n" +
            "order_dish.to_admin AS \"order_dish.to_admin\",\n" +
            "order_dish.user_id AS \"order_dish.user_id\",\n" +
            "order_dish.completed AS \"order_dish.completed\"\n" +
            "FROM order_dish WHERE order_dish.id = ?";

    @Override
    public Optional<OrderDish> findById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Map<Long, OrderDish> orders = extractFromResultSet(rs);
            return Optional.of(orders.values().stream().findFirst().get());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<OrderDish> findAll() {
        return null;
    }

    @Override
    public void update(OrderDish entity) {

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

    private static final String FIND_BY_USERNAME_QUERY = "SELECT order_dish.id AS \"order_dish.id\"\n" +
            "FROM order_dish\n" +
            "WHERE user_id = (SELECT id FROM users WHERE username=?) AND order_dish.completed = 0;";

    public Optional<Long> findUnCompletedForUser(String username) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_USERNAME_QUERY)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getLong("order_dish.id"));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String CREATE_ORDER = "INSERT INTO order_dish ( checked, payed, price_all, to_admin, user_id, completed )" +
            " VALUES (?, ?, ?, ?, ?, ?)";

    private static final String CREATE_ORDER_DISHES = "INSERT INTO order_dish_dishes ( orders_with_dish_id, dishes_id) VALUES (?, ?)";

    public void save(OrderDish orderDish) {

        try (PreparedStatement ps = connection.prepareStatement
                (CREATE_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, orderDish.isChecked());
            ps.setBoolean(2, orderDish.isPayed());
            ps.setInt(3, orderDish.getPriceAll().intValue());
            ps.setBoolean(4, orderDish.isToAdmin());
            ps.setLong(5, orderDish.getUser().getId());
            ps.setBoolean(6, orderDish.isCompleted());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Long id = rs.getLong(1);
                orderDish.setId(id);
            }
            List<Dish> dishes = orderDish.getDishes();

                for (Dish d : dishes){
                    try (PreparedStatement prs = connection.prepareStatement(CREATE_ORDER_DISHES)) {
                    prs.setLong(1, orderDish.getId());
                    prs.setLong(2, d.getId());
                        prs.executeUpdate();
                    }catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String UPDATE_ORDER_TO_ADMIN = "UPDATE order_dish SET to_admin = 1 , completed = 1 WHERE order_dish.id = ?";

    public void updateToAdmin(OrderDish orderDish) {

        try (PreparedStatement ps = connection.prepareStatement
                (UPDATE_ORDER_TO_ADMIN)) {
            ps.setLong(1, orderDish.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DELETE_ORDER_DISHES = "DELETE FROM order_dish_dishes odd where odd.orders_with_dish_id = ? AND odd.dishes_id=? limit 1";

    private static final String UPDATE_ORDER_DISHES = "UPDATE order_dish SET price_all = price_all - ? WHERE order_dish.id = ?";

    public void removeDish(OrderDish orderDish, Dish dish) {

        try (PreparedStatement ps = connection.prepareStatement
                (DELETE_ORDER_DISHES)) {
            ps.setLong(1, orderDish.getId());
            ps.setLong(2, dish.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement ps = connection.prepareStatement
                (UPDATE_ORDER_DISHES)) {
            ps.setLong(1, dish.getPrice().intValue());
            ps.setLong(2, orderDish.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String UPDATE_PRICE = "UPDATE order_dish SET price_all = price_all + ? WHERE order_dish.id = ?";

    public void addDishINOrder(OrderDish orderDish) {

            List<Dish> dishes = orderDish.getDishes();

            for (Dish d : dishes){
                try (PreparedStatement prs = connection.prepareStatement(CREATE_ORDER_DISHES)) {
                    prs.setLong(1, orderDish.getId());
                    prs.setLong(2, d.getId());
                    prs.executeUpdate();
                }catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        try (PreparedStatement ps = connection.prepareStatement
                (UPDATE_PRICE)) {
            ps.setLong(1, orderDish.getPriceAll().longValue());
            ps.setLong(2, orderDish.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static final String FIND_UNCHECKED_INDEX = "SELECT id AS id FROM order_dish WHERE order_dish.checked=0 AND order_dish.to_admin=1";

    public List<Long> findOrderUncheckedIndex() {
        try (PreparedStatement ps = connection.prepareStatement(FIND_UNCHECKED_INDEX)) {

            ResultSet resultSet = ps.executeQuery();

            List<Long> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(resultSet.getLong("id"));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String FIND_UNCONFIRMED_BY_ID = "SELECT id FROM order_dish WHERE order_dish.checked=1 AND order_dish.payed=0 AND order_dish.user_id=?";

    public List<Long> findUnConfirmed(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_UNCONFIRMED_BY_ID)) {
            ps.setLong(1,id);

            ResultSet resultSet = ps.executeQuery();

            List<Long> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(resultSet.getLong("id"));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String UPDATE_CHECKED ="UPDATE order_dish SET checked = 1 WHERE order_dish.id=?";

    public void setChecked(Long id) {
        try (PreparedStatement ps = connection.prepareStatement
                (UPDATE_CHECKED)) {
            ps.setLong(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String UPDATE_PAYED ="UPDATE order_dish SET payed = 1 WHERE order_dish.id=?";

    public void setPayed(Long id) {
        try (PreparedStatement ps = connection.prepareStatement
                (UPDATE_PAYED, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private LinkedHashMap<Long, OrderDish> extractFromResultSet(ResultSet rss) throws SQLException {

        OrderMapper orderMapper = new OrderMapper();
        LinkedHashMap<Long, OrderDish> orders = new LinkedHashMap<>();

        while (rss.next()) {
            OrderDish orderDish = orderMapper.extractFromResultSet(rss);
            orderMapper.makeUnique(orders, orderDish);
        }

        return orders;
    }
}
