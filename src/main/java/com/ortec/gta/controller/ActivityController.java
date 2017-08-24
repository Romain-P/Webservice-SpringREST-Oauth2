package com.ortec.gta.controller;

import com.ortec.gta.service.ActivityService;
import com.ortec.gta.common.controller.AbstractCrudController;
import com.ortec.gta.database.model.dto.ActivityDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 16/08/2017
 */

@RestController
@RequestMapping("/activity")
public class ActivityController extends AbstractCrudController<ActivityDTO, ActivityService> {

    @GetMapping("/parents")
    @ResponseStatus(HttpStatus.OK)
    public Set<ActivityDTO> getParents() {
        return getService().getParents();
    }

    @GetMapping("/parents/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Set<ActivityDTO> getParents(@PathVariable Integer id) {
        return getService().getParents(id);
    }
}
