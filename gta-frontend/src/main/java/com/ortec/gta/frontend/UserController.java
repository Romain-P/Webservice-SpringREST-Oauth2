package com.ortec.gta.frontend;

import com.ortec.gta.auth.Session;
import com.ortec.gta.domain.AbsenceDayDTO;
import com.ortec.gta.domain.UserDTO;
import com.ortec.gta.domain.UserIdentity;
import com.ortec.gta.service.AgirService;
import com.ortec.gta.service.UserService;
import com.ortec.gta.shared.AbstractCrudController;
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
@RestController
@RequestMapping("/user")
public class UserController extends AbstractCrudController<UserDTO, UserService> {
    @Autowired
    AgirService agirService;

    @GetMapping(value="/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getCurrent(@Session UserIdentity user) {
        return getService().get(user.getId())
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value="/current-meta-sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getCurrentMetaSync(@Session UserIdentity user) {
        return getService().getWithMetaCall(user.getId())
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value="/meta-sync/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getMetaSync(@PathVariable Long id) {
        return getService().getWithMetaCall(id)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value="/absenceDays/{userId}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Set<AbsenceDayDTO> getAbsenceDays(@PathVariable Long userId, @PathVariable int year) {
        return this.agirService.getAbsenceDays(userId, year);
    }

    @GetMapping(value="/absenceDays/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Set<AbsenceDayDTO> getCurrentAbsenceDays(@Session UserIdentity user, @PathVariable int year) {
        return this.agirService.getAbsenceDays(user.getId(), year);
    }
}
