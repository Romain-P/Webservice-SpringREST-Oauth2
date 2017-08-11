package com.ortec.ihm.clktime.rest.controller;

import com.ortec.ihm.clktime.rest.configuration.annotation.Tokened;
import com.ortec.ihm.clktime.rest.database.ActivityRepositoryImpl;
import com.ortec.ihm.clktime.rest.database.RoleRepositoryImpl;
import com.ortec.ihm.clktime.rest.database.model.dto.ActivityDTO;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.Activity;
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
    ActivityRepositoryImpl activityRepository;

    @Autowired
    UserRoleService roleService;

    @RequestMapping("/test")
    public ActivityDTO message(@Tokened UserDTO user){
        return activityRepository.findById(1).get();
    }

    @RequestMapping("/")
    public String hello(){
        return "Ortec restful application";
    }

    public static void main(String args[]) {}
}
