package com.ortec.gta.controller;

import com.ortec.gta.common.controller.AbstractCrudController;
import com.ortec.gta.database.model.dto.RoleDTO;
import com.ortec.gta.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: romain.pillot
 * @Date: 05/09/2017
 */
@RestController
@RequestMapping("/role")
public class RoleController extends AbstractCrudController<RoleDTO, RoleService> {}
