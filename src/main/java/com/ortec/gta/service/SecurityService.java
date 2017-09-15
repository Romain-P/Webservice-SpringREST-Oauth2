package com.ortec.gta.service;

import com.ortec.gta.database.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: romain.pillot
 * @Date: 07/09/2017
 */

@Service("securityService")
public class SecurityService {
    @Autowired
    UserService userService;

    public boolean isSuperiorOf(int requestUserId, Integer targetUserId) {
        UserDTO requester = userService.get(requestUserId).orElse(null);
        UserDTO target = userService.get(targetUserId).orElse(null);

        if (requester == null || target == null) return false;

        return true; //TODO: Check if the targeted user is a child of the requester user.
    }
}
