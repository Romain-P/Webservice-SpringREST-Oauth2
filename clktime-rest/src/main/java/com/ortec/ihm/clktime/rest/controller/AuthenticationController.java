package com.ortec.ihm.clktime.rest.controller;

import com.ortec.ihm.clktime.rest.configuration.annotation.Tokened;
import com.ortec.ihm.clktime.rest.database.RoleDAO;
import com.ortec.ihm.clktime.rest.database.model.dto.RoleDTO;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.Role;
import com.ortec.ihm.clktime.rest.database.repository.UserRepository;
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
    UserRepository repository;

    @Autowired
    RoleDAO roleDAO;

    @RequestMapping("/test")
    public RoleDTO message(@Tokened UserDTO user){
        roleDAO.create(new RoleDTO(0, "salut"));
        return null;
    }

    @RequestMapping("/")
    public String hello(){
        return "Ortec restful application";
    }
}
