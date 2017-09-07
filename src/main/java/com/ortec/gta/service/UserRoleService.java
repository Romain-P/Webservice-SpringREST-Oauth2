package com.ortec.gta.service;

import com.google.common.collect.ImmutableSet;
import com.ortec.gta.common.user.TokenedUser;
import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.RoleDTO;
import com.ortec.gta.database.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 31/07/2017
 */

@Service
public class UserRoleService {
    @Autowired
    UserService userService;

    /**
     * Do not call for grant or remote roles.
     *
     * @return the model list transformed to an immutable set of GrantedAuthority.
     * This method might be be called to create a new Authentication.
     */
    public ImmutableSet<GrantedAuthority> mapToAppRoles(Set<RoleDTO> roles) {
        return roles.stream()
                .map(x -> new SimpleGrantedAuthority(x.getName()))
                .collect(ImmutableSet.toImmutableSet());
    }

    public boolean rolesChanged(UserDTO user) {
        Set<RoleDTO> databaseRoles = user.getRoles();
        Collection<? extends GrantedAuthority> sessionRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        return databaseRoles.size() != sessionRoles.size() ||
                !sessionRoles.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())
                        .containsAll(databaseRoles.stream().map(RoleDTO::getName).collect(Collectors.toSet()));
    }

    /**
     * Replace the old connection by a new one: avoids a manual reconnection.
     * This is necessary to update the authority list, due of the Authentication authority list is unmodifiable.
     *
     * @param user user to refresh
     */
    public void refreshConnection(UserDTO user) {
        Authentication refreshed = new UsernamePasswordAuthenticationToken(new TokenedUser(user.getId()), null,
                mapToAppRoles(user.getRoles()));
        SecurityContextHolder.getContext().setAuthentication(refreshed);
    }
}
