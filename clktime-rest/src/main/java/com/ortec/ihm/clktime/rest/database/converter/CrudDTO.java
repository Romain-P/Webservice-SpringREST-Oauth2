package com.ortec.ihm.clktime.rest.database.converter;

import java.util.List;
import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 03/08/2017
 */

public interface CrudDTO<D> {
    Optional<D> findById(int id);
    List<D> findAll();

    void create(D dto);
    void update(D dto);
    void delete(D dto);
    void deleteById(int id);
}