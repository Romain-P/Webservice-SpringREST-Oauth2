package com.ortec.gta.service;

import com.ortec.gta.database.UserRepositoryImpl;
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
    UserRepositoryImpl userRepository;

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
    public boolean isSuperiorOf(int requestUserId, Integer targetUserId) {
        UserDTO requester = userRepository.findById(requestUserId).orElse(null);
        UserDTO target = userRepository.findById(targetUserId).orElse(null);

        return requester != null && target != null && isSuperiorOf(requester, target);
    }
}
