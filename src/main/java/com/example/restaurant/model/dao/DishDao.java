package com.example.restaurant.model.dao;

import com.example.restaurant.model.entity.Dish;

import java.util.List;

public interface DishDao extends GenericDao<Dish> {
    Dish findByName(String dishName);

    List<Dish> findByOrderToUser(Long id);

    List<Dish> findByOrder(Long id);

    List<Dish> findAllByOrderID(Long id);

    void deleteById(Long id);

    void save(Dish dish);
}
