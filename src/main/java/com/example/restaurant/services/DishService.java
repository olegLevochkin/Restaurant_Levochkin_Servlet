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

//    public Page<Dish> getAllDishes(Pageable pageable) {
//        int pageSize = pageable.getPageSize();
//        int currentPage = pageable.getPageNumber();
//        int startItem = currentPage * pageSize;
//        List<Dish> list;
//        List<Dish> dishes = dishRepository.findAll();
//
//        if (dishes.size() < startItem) {
//            list = Collections.emptyList();
//        } else {
//            int toIndex = Math.min(startItem + pageSize, dishes.size());
//            list = dishes.subList(startItem, toIndex);
//        }
//
//        Page<Dish> dishPage
//                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), dishes.size());
//
//        return dishPage;
//    }

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
    ////////////////////////////
    public List<Dish> findAllByOrderID(Long id) throws Exception {
        try (DishDao dao = daoFactory.createDishDao()) {
            return dao.findAllByOrderID(id);
        }
    }
    ///////////////////////////

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
