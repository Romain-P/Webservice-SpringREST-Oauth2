package com.ortec.ihm.clktime.rest.common.database;

import java.util.List;
import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 03/08/2017
 */

/**
 * Overlay of spring data basic CRUD for conversion between entity <-> dto
 *
 * @param <D> Dto Object
 */
public interface CrudDTO<D> {
    Optional<D> findById(int id);
    List<D> findAll();

    void create(D dto, boolean hasId);
    void update(D dto);
    void delete(D dto);
    void deleteById(int id);
}