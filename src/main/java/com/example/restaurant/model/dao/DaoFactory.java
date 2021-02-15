package com.example.restaurant.model.dao;

import com.example.restaurant.model.dao.imp.JDBCDaoFactory;
import com.example.restaurant.model.dao.imp.JDBCDishDao;
import com.example.restaurant.model.dao.imp.JDBCOrderDao;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();

    public abstract ProductDao createProductDao();

    public abstract JDBCOrderDao createOrderDao();

    public abstract JDBCDishDao createDishDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }

}

