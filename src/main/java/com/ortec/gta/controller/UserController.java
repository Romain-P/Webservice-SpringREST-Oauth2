package com.ortec.gta.controller;

import com.ortec.gta.common.controller.AbstractCrudController;
import com.ortec.gta.database.model.dto.UserDTO;
import com.ortec.gta.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: romain.pillot
 * @Date: 16/08/2017
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractCrudController<UserDTO, UserService> {}
