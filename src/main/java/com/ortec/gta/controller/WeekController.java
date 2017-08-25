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

    @GetMapping("/user/{userId}/weekNumber/{weekNumber}/year/{year}")
    @ResponseStatus(HttpStatus.OK)
    public Set<WeekDTO> getParentsByWeek(@PathVariable Integer userId, @PathVariable Integer weekNumber, @PathVariable Integer year) {
        return getService().getUserWeeks(userId, weekNumber, year);
    }

    @GetMapping("/user/weekNumber/{weekNumber}/year/{year}")
    @ResponseStatus(HttpStatus.OK)
    public Set<WeekDTO> getParentsByWeek(@Tokened TokenedUser user, @PathVariable Integer weekNumber, @PathVariable Integer year) {
        return getService().getUserWeeks(user.getId(), weekNumber, year);
    }
}
