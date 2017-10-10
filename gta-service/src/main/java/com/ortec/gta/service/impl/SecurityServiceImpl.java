package com.ortec.gta.service.impl;

import com.ortec.gta.database.dao.UserDAO;
import com.ortec.gta.domain.UserDTO;
import com.ortec.gta.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: romain.pillot
 * @Date: 07/09/2017
 */

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    UserDAO userRepository;

    private boolean isSuperiorOf(UserDTO requester, UserDTO target) {
        return requester.getChildren().stream()
                .anyMatch(x -> x.getId().equals(target.getId()) ||
                        (!x.getId().equals(requester.getId()) && isSuperiorOf(x, target)));
    }

    /**
     * @param requestUserId the user trying to edit the target
     * @param targetUserId the targeted user
     *
     * @return true if the requester is a parent direct/indirect of the target
     */
    public boolean isSuperiorOf(Long requestUserId, Long targetUserId) {
        UserDTO requester = userRepository.findById(requestUserId).orElse(null);
        UserDTO target = userRepository.findById(targetUserId).orElse(null);

        return requester != null && target != null && isSuperiorOf(requester, target);
    }
}
