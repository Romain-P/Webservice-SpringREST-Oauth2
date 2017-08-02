package com.ortec.ihm.clktime.rest.database;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */
public interface DAO<T> {
    void create(T object);
    void delete(T object);
    void update(T object);
    T findById(long id);
}
