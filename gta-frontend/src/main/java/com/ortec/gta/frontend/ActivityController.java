package com.ortec.gta.frontend;

import com.ortec.gta.domain.ActivityDTO;
import com.ortec.gta.service.ActivityService;
import com.ortec.gta.shared.AbstractCrudController;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/children/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Set<ActivityDTO> getChildren(@PathVariable Long id) {
        return getService().getChildren(id);
    }
}
