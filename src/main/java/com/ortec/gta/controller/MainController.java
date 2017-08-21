package com.ortec.gta.controller;

import com.ortec.gta.common.user.TokenedUser;
import com.ortec.gta.configuration.annotation.Tokened;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */

@RestController
public class MainController {
    @RequestMapping("/hello")
    public TokenedUser hello(@Tokened TokenedUser user) {
        return user;
    }
}
