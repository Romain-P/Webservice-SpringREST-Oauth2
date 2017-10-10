package com.ortec.gta.shared;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 18/08/2017
 */
public abstract class AbstractCrudService<D, R extends AbstractDAO<?, ?, D>> implements CrudService<D> {
    @Getter
    @Autowired
    private R repository;

    public void create(D dto) {
        repository.create(dto, true);
    }

    public void delete(D dto) {
        repository.delete(dto);
    }

    public Optional<D> get(Long id) {
        return repository.findById(id);
    }

    public Set<D> get() {
        return repository.findAll();
    }

    public void update(D dto) {
        repository.update(dto);
    }
}
