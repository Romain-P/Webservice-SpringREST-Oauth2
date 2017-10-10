package com.ortec.gta.shared;

import java.util.Optional;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/10/2017
 */
public interface CrudService<D> {
    void create(D dto);
    void delete(D dto);
    void update(D dto);
    Optional<D> get(Long id);
    Set<D> get();
}
