package com.ortec.ihm.clktime.rest.controller;

import com.ortec.ihm.clktime.rest.common.controller.AbstractCrudController;
import com.ortec.ihm.clktime.rest.database.UserRepositoryImpl;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: romain.pillot
 * @Date: 16/08/2017
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractCrudController<UserDTO, UserRepositoryImpl> {}
