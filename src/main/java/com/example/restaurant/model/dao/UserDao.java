package com.example.restaurant.model.dao;

import com.example.restaurant.model.entity.User;
import com.example.restaurant.services.BankTransactionException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;

public interface UserDao extends GenericDao<User>{
    boolean isDuplicateUsername(String username);

    User findByUsername(String username);

    Long findUserId(String username);

    void saveNewUser(User user) ;

    void saveBestProduct(User user) ;

    void payForOrder(BigInteger sum, String username) throws SQLException, BankTransactionException;

}
