package com.example.restaurant.model.dao;

import com.example.restaurant.model.entity.Dish;
import com.example.restaurant.model.entity.OrderDish;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends GenericDao<OrderDish> {
    void setPayed(Long indOrder);

    void setChecked(Long indOrder);

    List<Long> findUnConfirmed(Long id);

    List<Long> findOrderUncheckedIndex();

    void save(OrderDish orderDish);

    void updateToAdmin(OrderDish orderDish);

    void removeDish(OrderDish orderDish, Dish dish);

    void addDishINOrder(OrderDish orderDish);

    Optional<Long> findUnCompletedForUser(String username);
}
