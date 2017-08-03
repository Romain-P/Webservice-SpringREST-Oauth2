package com.ortec.ihm.clktime.rest.controller;

import com.ortec.ihm.clktime.rest.configuration.annotation.Tokened;
import com.ortec.ihm.clktime.rest.database.RoleRepositoryImpl;
import com.ortec.ihm.clktime.rest.database.model.dto.RoleDTO;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import com.ortec.ihm.clktime.rest.service.authority.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */

@RestController
public class AuthenticationController {
    @Autowired
    RoleRepositoryImpl roleRepository;

    @Autowired
    UserRoleService roleService;

    @RequestMapping("/test")
    public UserDTO message(@Tokened UserDTO user){
        roleService.grantUser(user, new RoleDTO(1, "admin"));
        return user;
    }

    @RequestMapping("/")
    public String hello(){
        return "Ortec restful application";
    }
}
