package com.ortec.gta.frontend;

import com.ortec.gta.auth.Session;
import com.ortec.gta.domain.UserIdentity;
import com.ortec.gta.domain.WeekDTO;
import com.ortec.gta.service.WeekService;
import com.ortec.gta.shared.AbstractCrudController;
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
    public Set<WeekDTO> getParentsByWeek(@PathVariable Long userId, @PathVariable Integer weekNumber, @PathVariable Integer year) {
        return getService().getUserWeeks(userId, weekNumber, year);
    }

    @GetMapping("/user/weekNumber/{weekNumber}/year/{year}")
    @ResponseStatus(HttpStatus.OK)
    public Set<WeekDTO> getParentsByWeek(@Session UserIdentity user, @PathVariable Integer weekNumber, @PathVariable Integer year) {
        return getService().getUserWeeks(user.getId(), weekNumber, year);
    }
}
