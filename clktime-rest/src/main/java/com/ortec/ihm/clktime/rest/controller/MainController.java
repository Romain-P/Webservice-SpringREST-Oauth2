package com.ortec.ihm.clktime.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */

@RestController
public class MainController {

    @RequestMapping("/")
    public String hello(){
        return "Ortec restful application";
    }

}
