package com.ortec.gta.controller;

import com.ortec.gta.service.ActivityService;
import com.ortec.gta.common.controller.AbstractCrudController;
import com.ortec.gta.database.model.dto.ActivityDTO;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: romain.pillot
 * @Date: 16/08/2017
 */

@RestController
@RequestMapping("/activity")
public class ActivityController extends AbstractCrudController<ActivityDTO, ActivityService> {}
