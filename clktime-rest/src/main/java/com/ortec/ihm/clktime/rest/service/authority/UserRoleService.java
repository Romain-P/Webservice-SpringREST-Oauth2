package com.ortec.ihm.clktime.rest.service.authority;

import com.ortec.ihm.clktime.rest.model.dto.GlobalUser;
import com.ortec.ihm.clktime.rest.model.entity.Role;
import com.ortec.ihm.clktime.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @Author: romain.pillot
 * @Date: 31/07/2017
 */

@Service
public class UserRoleService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Grant user rights.
     *
     * @param user a GlobalUser (might be get by @Tokened)
     * @param role a role to grant.
     */
    public void grantUser(GlobalUser user, Role role) {
        if (user.getModel().getRoles().contains(role))
            return;
        user.getModel().getRoles().add(role);
        refreshConnection(user);
    }

    /**
     * Demote user rights.
     *
     * @param user a GlobalUser (might be get by @Tokened)
     * @param role a role to grant.
     */
    public void demoteUser(GlobalUser user, Role role) {
        if (!user.getModel().getRoles().contains(role))
            return;
        user.getModel().getRoles().remove(role);
        refreshConnection(user);
    }

    /**
     * Replace the old connection by a new one: avoids a manual reconnection.
     * This is necessary to update the authority list, due of the Authentication authority list is unmodifiable.
     *
     * @param user user to refresh
     */
    private void refreshConnection(GlobalUser user) {
        Authentication refreshed = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
        SecurityContextHolder.getContext().setAuthentication(refreshed);
    }
}
