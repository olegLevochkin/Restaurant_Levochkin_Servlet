package com.example.restaurant.services;

import com.example.restaurant.model.dao.DaoFactory;
import com.example.restaurant.model.dao.UserDao;
import com.example.restaurant.model.entity.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

public class UserService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public UserService() {
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

    public Long getUserIdByUsername(String username) throws Exception {
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
            dao.payForOrder(sum, username);
        }
    }

}
