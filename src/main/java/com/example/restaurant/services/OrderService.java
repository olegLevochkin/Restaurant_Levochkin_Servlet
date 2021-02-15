package com.example.restaurant.services;

import com.example.restaurant.model.dao.DaoFactory;
import com.example.restaurant.model.dao.OrderDao;
import com.example.restaurant.model.dao.imp.JDBCDaoFactory;
import com.example.restaurant.model.entity.Dish;
import com.example.restaurant.model.entity.OrderDish;

import java.util.List;
import java.util.Optional;

public class OrderService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public OrderService() {
    }

    public Optional<Long> getUnCompletedForUser(String username) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            return dao.findUnCompletedForUser(username);
        }
    }

    public void saveOrder(OrderDish orderDish) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            dao.save(orderDish);
        }
    }

    public void updateToAdmin(OrderDish orderDish) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            dao.updateToAdmin(orderDish);
        }
    }

    public void removeDish(OrderDish orderDish, Dish dish) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            dao.removeDish(orderDish, dish);
        }
    }


    public void addDishINOrder(OrderDish orderDish) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            dao.addDishINOrder(orderDish);
        }
    }

    public Optional<OrderDish> getByID(int id) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            return dao.findById(id);
        }
    }

    public List<Long> confirmToAdmin() throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            return dao.findOrderUncheckedIndex();
        }
    }

    public List<Long> confirmToUser(Long id) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            return dao.findUnConfirmed(id);
        }
    }

    public void confirm(Long indOrder) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            dao.setChecked(indOrder);
        }
    }

    public void payed(Long indOrder) throws Exception {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            dao.setPayed(indOrder);
        }
    }
}
