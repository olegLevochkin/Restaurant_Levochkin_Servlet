package com.example.restaurant.services;

import com.example.restaurant.model.dao.DaoFactory;
import com.example.restaurant.model.dao.DishDao;
import com.example.restaurant.model.dao.imp.JDBCDaoFactory;
import com.example.restaurant.model.entity.Dish;

import java.util.List;
import java.util.Set;

public class DishService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    private JDBCDaoFactory jdbcDaoFactory;

    public DishService() {
        this.jdbcDaoFactory = new JDBCDaoFactory();
    }

    public Set<Dish> getAllDishes() throws Exception {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findAll();
        }
    }

    public void saveDish(Dish dish) throws Exception {
        try (DishDao dao = daoFactory.createDishDao()) {
            dao.save(dish);
        }
    }

    public void deleteByID(Long id) throws Exception {
        try (DishDao dao = daoFactory.createDishDao()) {
            dao.deleteById(id);
        }
    }

    public List<Dish> findByOrderID(Long id) throws Exception {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findByOrder(id);
        }
    }

    public List<Dish> findByOrderIDToUSer(Long id) throws Exception {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findByOrderToUser(id);
        }
    }

    public Dish findByDishName(String dishName) throws Exception {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findByName(dishName);
        }
    }
}
