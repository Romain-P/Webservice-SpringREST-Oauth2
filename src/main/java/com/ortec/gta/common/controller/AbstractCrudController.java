package com.ortec.gta.common.controller;

import com.ortec.gta.common.service.AbstractCrudService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 16/08/2017
 */
public abstract class AbstractCrudController<D, S extends AbstractCrudService<D, ?>> {

    @Getter
    @Autowired
    private S service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody D dto) {
        service.create(dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> get(@PathVariable Integer id) {
        return service.get(id)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<D> get() {
        return service.get();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody D dto) {
        service.update(dto);
    }
}
