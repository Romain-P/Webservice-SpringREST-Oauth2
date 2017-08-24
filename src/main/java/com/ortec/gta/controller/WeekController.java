package com.ortec.gta.controller;

import com.ortec.gta.common.controller.AbstractCrudController;
import com.ortec.gta.common.user.TokenedUser;
import com.ortec.gta.configuration.annotation.Tokened;
import com.ortec.gta.database.model.dto.WeekDTO;
import com.ortec.gta.service.WeekService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 24/08/2017
 */
@RestController
@RequestMapping("/week")
public class WeekController extends AbstractCrudController<WeekDTO, WeekService> {
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Set<WeekDTO> getParents(@PathVariable Integer id) {
        return getService().getUserWeeks(id);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public Set<WeekDTO> getTokenedParents(@Tokened TokenedUser user) {
        return getService().getUserWeeks(user.getId());
    }
}
