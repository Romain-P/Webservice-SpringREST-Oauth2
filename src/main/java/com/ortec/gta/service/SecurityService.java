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

    private boolean isSuperiorOf(UserDTO requester, UserDTO target) {
        if (requester == null || target == null) return false;

        for (UserDTO child: requester.getChildren())
            if (child.getId().equals(target.getId()) || isSuperiorOf(child, target))
                return true;
        return false;
    }

    /**
     * @param requestUserId the user trying to edit the target
     * @param targetUserId the targeted user
     *
     * @return true if the requester is a parent direct/indirect of the target
     */
    public boolean isSuperiorOf(int requestUserId, Integer targetUserId) {
        UserDTO requester = userService.get(requestUserId).orElse(null);
        UserDTO target = userService.get(targetUserId).orElse(null);

        return isSuperiorOf(requester, target);
    }
}
