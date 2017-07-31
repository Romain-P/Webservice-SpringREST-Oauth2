package com.ortec.ihm.clktime.rest.controller;

import com.ortec.ihm.clktime.rest.configuration.annotation.Tokened;
import com.ortec.ihm.clktime.rest.model.dto.GlobalUser;
import com.ortec.ihm.clktime.rest.model.entity.User;
import com.ortec.ihm.clktime.rest.repository.UserRepository;
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

    @RequestMapping("/test")
    public User message(@Tokened GlobalUser user){
        return user.getModel();
    }

    @RequestMapping("/")
    public String hello(){
        return "Ortec restful application";
    }
}
