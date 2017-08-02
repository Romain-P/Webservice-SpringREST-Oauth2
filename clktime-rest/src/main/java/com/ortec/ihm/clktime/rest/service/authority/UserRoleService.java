package com.ortec.ihm.clktime.rest.service.authority;

import com.google.common.collect.ImmutableSet;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.Role;
import com.ortec.ihm.clktime.rest.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

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
     * @param user a UserDTO (might be get by @Tokened)
     * @param role a role to grant.
     */
    public void grantUser(UserDTO user, Role role) {
        if (user.getModel().getRoles().contains(role))
            return;
        user.getModel().getRoles().add(role);
        refreshConnection(user);
    }

    /**
     * Demote user rights.
     *
     * @param user a UserDTO (might be get by @Tokened)
     * @param role a role to grant.
     */
    public void demoteUser(UserDTO user, Role role) {
        if (!user.getModel().getRoles().contains(role))
            return;
        user.getModel().getRoles().remove(role);
        refreshConnection(user);
    }

    /**
     * Do not call for grant or remote roles.
     *
     * @return the model list transformed to an immutable set of GrantedAuthority.
     *         This method might be be called to create a new Authentication.
     */
    public ImmutableSet<GrantedAuthority> mapToAppRoles(Set<Role> roles) {
        return roles.stream()
                .map(x -> new SimpleGrantedAuthority(x.getName()))
                .collect(ImmutableSet.toImmutableSet());
    }

    /**
     * Replace the old connection by a new one: avoids a manual reconnection.
     * This is necessary to update the authority list, due of the Authentication authority list is unmodifiable.
     *
     * @param user user to refresh
     */
    private void refreshConnection(UserDTO user) {
        Authentication refreshed = new UsernamePasswordAuthenticationToken(user, null,
                mapToAppRoles(user.getModel().getRoles()));
        SecurityContextHolder.getContext().setAuthentication(refreshed);
    }
}
