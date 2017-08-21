package com.ortec.ihm.clktime.rest.service;

import com.google.common.collect.ImmutableSet;
import com.ortec.ihm.clktime.rest.database.UserRepositoryImpl;
import com.ortec.ihm.clktime.rest.database.model.dto.RoleDTO;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
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
    private UserRepositoryImpl userRepository;

    /**
     * Grant user rights.
     *
     * @param user a UserDTO (might be get by @Tokened)
     * @param role a role to grant.
     */
    public void grantUser(UserDTO user, RoleDTO role) {
        if (user.getRoles().contains(role))
            return;
        user.getRoles().add(role);
        userRepository.update(user);
        refreshConnection(user);
    }

    /**
     * Demote user rights.
     *
     * @param user a UserDTO (might be get by @Tokened)
     * @param role a role to grant.
     */
    public void demoteUser(UserDTO user, RoleDTO role) {
        if (!user.getRoles().contains(role))
            return;
        user.getRoles().remove(role);
        userRepository.update(user);
        refreshConnection(user);
    }

    /**
     * Do not call for grant or remote roles.
     *
     * @return the model list transformed to an immutable set of GrantedAuthority.
     *         This method might be be called to create a new Authentication.
     */
    public ImmutableSet<GrantedAuthority> mapToAppRoles(Set<RoleDTO> roles) {
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
                mapToAppRoles(user.getRoles()));
        SecurityContextHolder.getContext().setAuthentication(refreshed);
    }
}
