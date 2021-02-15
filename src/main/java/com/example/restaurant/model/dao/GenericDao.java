package com.example.restaurant.model.dao;

import com.example.restaurant.exception.DeleteDependentException;

import java.util.Optional;
import java.util.Set;

public interface GenericDao<T> extends AutoCloseable {

    void create(T entity);
    Optional<T> findById(int id);
    Set<T> findAll();
    void update(T entity);
    void delete(int id) throws DeleteDependentException;
}
