package com.ortec.ihm.clktime.rest.common.service;

import com.ortec.ihm.clktime.rest.common.database.CrudRepositoryDtoConverter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 18/08/2017
 */
public abstract class AbstractCrudService<D, R extends CrudRepositoryDtoConverter<?, ?, D>> {
    @Getter
    @Autowired
    private R repository;

    public void create(D dto) {
        repository.create(dto, true);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Optional<D> get(Integer id) {
        return repository.findById(id);
    }

    public Set<D> get() {
        return repository.findAll();
    }

    public void update(D dto) {
        repository.update(dto);
    }
}
