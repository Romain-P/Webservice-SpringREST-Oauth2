package com.ortec.gta.controller;

import com.ortec.gta.common.controller.AbstractCrudController;
import com.ortec.gta.common.user.TokenedUser;
import com.ortec.gta.configuration.annotation.Tokened;
import com.ortec.gta.database.model.dto.AbsenceDayDTO;
import com.ortec.gta.database.model.dto.UserDTO;
import com.ortec.gta.service.AgirService;
import com.ortec.gta.service.UserService;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 16/08/2017
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractCrudController<UserDTO, UserService> {
    @Autowired
    AgirService agirService;

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

    @GetMapping(value="/absenceDays/{userId}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Set<AbsenceDayDTO> getAbsenceDays(@PathVariable int userId, @PathVariable int year) {
        return this.agirService.getAbsenceDays(userId, year);
    }

    @GetMapping(value="/absenceDays/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Set<AbsenceDayDTO> getCurrentAbsenceDays(@Tokened TokenedUser user, @PathVariable int year) {
        return this.agirService.getAbsenceDays(user.getId(), year);
    }
}
