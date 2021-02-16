package com.example.restaurant.model.dao.imp;

import com.example.restaurant.exception.DeleteDependentException;
import com.example.restaurant.model.dao.UserDao;
import com.example.restaurant.model.dao.mapper.UserMapper;
import com.example.restaurant.model.entity.Role;
import com.example.restaurant.model.entity.User;
import com.example.restaurant.services.BankTransactionException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class JDBCUserDao implements UserDao {

    private static final String FIND_BY_ID_QUERY = "SELECT" +
            " users.id AS \"users.id\"," +
            " users.first_name AS \"users.first_name\"," +
            " users.last_name AS \"users.last_name\"," +
            " users.password AS \"users.password\"," +
            " users.balance AS \"users.balance\"," +
            " users.username AS \"users.username\"" +
            " FROM users\n" +
            " WHERE users.id = ?";

    private static final String UPDATE_QUERY = "UPDATE users SET first_name = ?," +
            " last_name = ?, username = ?, password = ? WHERE id = ?";

    private static final String DELETE_AUTHORITY_QUERY = "DELETE FROM user_authorities" +
            " WHERE user_id = ?";

    private static final String EXTRACT_AUTHORITIES_QUERY = "SELECT" +
            " users.id AS \"users.id\"," +
            " user_authorities.user_id AS \"user_authorities.user_id\"," +
            " user_authorities.authorities AS \"user_authorities.authorities\"" +
            " FROM users LEFT JOIN user_authorities" +
            " ON users.id=user_authorities.user_id" +
            " WHERE users.id=?";


    private final Connection connection;

    JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    private static final String UPDATE_BALANCE = "UPDATE users SET balance = ? WHERE users.id = ?";

    public void saveNewUser(User user) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_BALANCE)) {
            ps.setBigDecimal(1, user.getBalance());
            ps.setLong(2, user.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String UPDATE_BEST_PROSUCT = "UPDATE users SET product_id = ? WHERE users.id = ?";

    public void saveBestProduct(User user) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_BEST_PROSUCT)) {
            ps.setLong(1, user.getProdLikeNow().getId());
            ps.setLong(2, user.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(int id) {

        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Map<Long, User> users = extractFromResultSet(rs);
            return Optional.ofNullable(users.get(id));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String FIND_ALL_QUERY = "SELECT" +
            " users.id AS \"users.id\"," +
            " users.first_name AS \"users.first_name\"," +
            " users.last_name AS \"users.last_name\"," +
            " users.balance AS \"users.balance\"," +
            " users.password AS \"users.password\"," +
            " users.username AS \"users.username\"" +
            " FROM users" +
            " ORDER BY users.id";

    @Override
    public Set<User> findAll() {
        LinkedHashSet<User> result;

        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_QUERY)) {

            ResultSet rs = ps.executeQuery();
            LinkedHashMap<Long, User> users = extractFromResultSet(rs);
            result = new LinkedHashSet<>(users.values());

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String IS_DUPLICATE_QUERY = "SELECT users.username AS \"username\" FROM users WHERE username = ?";

    public boolean isDuplicateUsername(String username) {

        try (PreparedStatement ps = connection.prepareStatement(IS_DUPLICATE_QUERY)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            String result = "";

            while (rs.next()) {
                result = rs.getString("username");
            }

            return !result.equals("");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static final String CREATE_QUERY = "INSERT INTO users (first_name, last_name," +
            " username, password, balance) VALUES (? ,?, ?, ?, ?)";

    private static final String INSERT_INTO_AUTHORITIES_QUERY = "INSERT INTO user_authorities" +
            " (user_id, authorities) VALUES (?, ?)";

    @Override
    public void create(User entity) {

        try (PreparedStatement ps = connection.prepareStatement
                (CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getUsername());
            ps.setString(4, entity.getPassword());
            ps.setBigDecimal(5, BigDecimal.ZERO);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Long id = rs.getLong(1);
                entity.setId(id);
            }

            try (PreparedStatement aps = connection.prepareStatement(INSERT_INTO_AUTHORITIES_QUERY)) {
                aps.setLong(1, entity.getId());
                aps.setString(2, Role.USER.name());
                aps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(User entity) {
        try (PreparedStatement ps =
                     connection.prepareStatement(UPDATE_QUERY)) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getUsername());
            ps.setString(4, entity.getPassword());
            ps.setLong(5, entity.getId());
            ps.executeUpdate();

            try (PreparedStatement authorityDeletePS = connection.prepareStatement(
                    DELETE_AUTHORITY_QUERY)) {
                authorityDeletePS.setLong(1, entity.getId());
                authorityDeletePS.executeUpdate();
            }
            try (PreparedStatement authorityInsertPS = connection.prepareStatement(
                    INSERT_INTO_AUTHORITIES_QUERY)) {
                for (Role authority : entity.getAuthorities()) {
                    authorityInsertPS.setLong(1, entity.getId());
                    authorityInsertPS.setString(2, authority.name());
                    authorityInsertPS.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private static final String FIND_BY_USERNAME_QUERY = "SELECT" +
            " users.id AS \"users.id\"," +
            " users.first_name AS \"users.first_name\"," +
            " users.last_name AS \"users.last_name\"," +
            " users.password AS \"users.password\"," +
            " users.balance AS \"users.balance\"," +
            " users.username AS \"users.username\"," +
            " users.product_id AS \"users.product_id\"" +
            " FROM users WHERE username =?";

    public User findByUsername(String username) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_USERNAME_QUERY)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            Map<Long, User> users = extractFromResultSet(rs);

            return users.values().stream().findFirst().get();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Long findUserId(String username) {

        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_USERNAME_QUERY)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            Map<Long, User> users = extractFromResultSet(rs);

            return users.values().stream().findFirst().get().getId();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void payForOrder(BigInteger sum, String username) throws SQLException, BankTransactionException {

        try {
            connection.setAutoCommit(false);
            User user = findByUsername(username);

            if (user.getBalance().compareTo(BigDecimal.valueOf(sum.intValue())) < 0) {
                throw new BankTransactionException("The money in the account is not enough");
            } else {
                User admin = findByUsername("FirstUser");
                admin.setBalance(admin.getBalance().add(BigDecimal.valueOf(sum.intValue())));
                sum = sum.multiply(BigInteger.valueOf(-1));
                user.setBalance(user.getBalance().add(BigDecimal.valueOf(sum.intValue())));
                saveNewUser(admin);
                saveNewUser(user);
                connection.commit();
            }


        } catch (BankTransactionException e) {
            connection.rollback();
            throw new BankTransactionException("The money in the account is not enough");
        }
    }

    private LinkedHashMap<Long, User> extractFromResultSet(ResultSet rss) throws SQLException {

        UserMapper userMapper = new UserMapper();
        LinkedHashMap<Long, User> users = new LinkedHashMap<>();

        while (rss.next()) {
            User user = userMapper.extractFromResultSet(rss);
            userMapper.makeUnique(users, user);
        }

        for (User e : users.values()) {

            try (PreparedStatement ps = connection.prepareStatement(
                    EXTRACT_AUTHORITIES_QUERY)) {

                ps.setLong(1, e.getId());
                ResultSet rsa = ps.executeQuery();

                while (rsa.next()) {
                    String authority = rsa.getString("user_authorities.authorities");
                    e.addAuthority(Role.valueOf(authority));
                }

            }
        }

        return users;
    }


}
