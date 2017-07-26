package com.ortec.ihm.clktime.rest.controller;

import com.ortec.ihm.clktime.rest.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */

@RestController
public class AuthenticationController {
    @RequestMapping("/test")
    public User message(){
        return new User();
    }

    @RequestMapping("/")
    public User hello(){
        return new User();
    }
}
