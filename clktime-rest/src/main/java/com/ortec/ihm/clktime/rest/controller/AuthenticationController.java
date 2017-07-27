package com.ortec.ihm.clktime.rest.controller;

import com.ortec.ihm.clktime.rest.configuration.annotations.Tokened;
import com.ortec.ihm.clktime.rest.model.dto.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */

@RestController
public class AuthenticationController {

    @RequestMapping("/test")
    public User message(@Tokened User user){
        return user;
    }

    @RequestMapping("/")
    public String hello(){
        return "Ortec restful application";
    }
}
