package com.ortec.gta.controller;

import com.ortec.gta.database.model.dto.StatDTO;
import com.ortec.gta.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: romain.pillot
 * @Date: 14/09/2017
 */
@RestController
@RequestMapping("/stat")
public class StatController {
    @Autowired
    private StatService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StatDTO get() {
        return service.getStatistics();
    }
}
