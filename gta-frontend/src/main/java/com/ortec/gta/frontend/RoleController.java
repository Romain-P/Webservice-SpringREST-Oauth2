package com.ortec.gta.frontend;

import com.ortec.gta.domain.RoleDTO;
import com.ortec.gta.service.RoleService;
import com.ortec.gta.shared.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: romain.pillot
 * @Date: 05/09/2017
 */
@RestController
@RequestMapping("/role")
public class RoleController extends AbstractCrudController<RoleDTO, RoleService> {}
