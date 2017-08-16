package com.ortec.ihm.clktime.rest.common.controller;

import com.ortec.ihm.clktime.rest.common.database.CrudRepositoryDtoConverter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: romain.pillot
 * @Date: 16/08/2017
 */
public abstract class AbstractCrudController<D, R extends CrudRepositoryDtoConverter<?, ?, D>> {

    @Getter
    @Autowired
    private R repository;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody D dto) {
        repository.create(dto, true);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> get(@PathVariable Integer id) {
        return repository.findById(id)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{dto}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable D dto) {
        repository.update(dto);
    }
}
