package com.ortec.ihm.clktime.rest.controller;

import com.ortec.ihm.clktime.rest.common.controller.AbstractCrudController;
import com.ortec.ihm.clktime.rest.database.ActivityRepositoryImpl;
import com.ortec.ihm.clktime.rest.database.model.dto.ActivityDTO;
import com.ortec.ihm.clktime.rest.service.ActivityService;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: romain.pillot
 * @Date: 16/08/2017
 */

@RestController
@RequestMapping("/activity")
public class ActivityController extends AbstractCrudController<ActivityDTO, ActivityService> {}
