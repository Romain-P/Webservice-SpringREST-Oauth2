package com.ortec.ihm.clktime.rest.service.role;

import com.ortec.ihm.clktime.rest.model.dto.GlobalUser;
import com.ortec.ihm.clktime.rest.model.entity.Role;
import com.ortec.ihm.clktime.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

    public void grantUser(GlobalUser user, Role role) {
        if (user.getModel().getRoles().contains(role))
            return;
        user.getModel().getRoles().add(role);
        refreshConnection(user);
    }

    public void demoteUser(GlobalUser user, Role role) {
        if (!user.getModel().getRoles().contains(role))
            return;
        user.getModel().getRoles().remove(role);
        refreshConnection(user);
    }

    /**
     * Replace the old connection by a new one: avoids a manual reconnection.
     * This is necessary to update the role list, due of the Authentication role list is unmodifiable.
     *
     * @param user user to refresh
     */
    private void refreshConnection(GlobalUser user) {
        Authentication refreshed = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
        SecurityContextHolder.getContext().setAuthentication(refreshed);
    }
}
