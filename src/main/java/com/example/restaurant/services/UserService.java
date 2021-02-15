package com.example.restaurant.services;

import com.example.restaurant.model.dao.DaoFactory;
import com.example.restaurant.model.dao.UserDao;
import com.example.restaurant.model.dao.imp.JDBCDaoFactory;
import com.example.restaurant.model.entity.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

public class UserService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    private JDBCDaoFactory jdbcDaoFactory;

    public UserService() {
        this.jdbcDaoFactory = new JDBCDaoFactory();
    }

    public User getByUsername(String username) throws Exception {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findByUsername(username);
        }
    }

    public void registerUser(User user) throws Exception {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.create(user);
        }
    }

    public Long getUserIdByUsername(String username) throws Exception{
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findUserId(username);
        }
    }

    public boolean isDuplicateUsername(String username) throws Exception {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.isDuplicateUsername(username);
        }
    }

    public Set<User> findAll() throws Exception {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findAll();
        }
    }
    public User findById(int id) throws Exception {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findById(id).get();
        }
    }
    public void saveNewUser(User user) throws Exception {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.saveNewUser(user);
        }
    }

    public void saveBestProduct(User user) throws Exception {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.saveBestProduct(user);
        }
    }

    public void payForOrder(BigInteger sum, String username) throws Exception {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.payForOrder(sum, username );
        }
    }

    public void payTheOrder(BigInteger sum, String username) throws BankTransactionException {

        try {
            if (getByUsername(username).getBalance().compareTo(BigDecimal.valueOf(sum.intValue())) < 0) {
                throw new BankTransactionException(
                        "The money in the account is not enough ("
                                + getByUsername(username).getBalance() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        addMoney(sum, "someuser");
        addMoney(sum.multiply(BigInteger.valueOf(-1)), username);
    }

    public void addMoney(BigInteger moneyToAdd, String username) {
        User user = null;
        try {
            user = getByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setBalance(user.getBalance().add(BigDecimal.valueOf(moneyToAdd.intValue())));
        try {
            saveNewUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
