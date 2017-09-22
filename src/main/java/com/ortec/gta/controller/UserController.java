package com.ortec.gta.controller;

import com.ortec.gta.common.controller.AbstractCrudController;
import com.ortec.gta.common.user.TokenedUser;
import com.ortec.gta.configuration.annotation.Tokened;
import com.ortec.gta.database.model.dto.UserDTO;
import com.ortec.gta.service.UserService;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 16/08/2017
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractCrudController<UserDTO, UserService> {

    @GetMapping(value="/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getCurrent(@Tokened TokenedUser user) {
        return getService().get(user.getId())
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value="/current-meta-sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getCurrentMetaSync(@Tokened TokenedUser user) {
        return getService().getWithMetaCall(user.getId())
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value="/meta-sync/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getMetaSync(@PathVariable Integer id) {
        return getService().getWithMetaCall(id)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
