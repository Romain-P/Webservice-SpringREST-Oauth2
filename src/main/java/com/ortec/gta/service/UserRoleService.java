package com.ortec.gta.service;

import com.google.common.collect.ImmutableSet;
import com.ortec.gta.common.user.TokenedUser;
import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.RoleDTO;
import com.ortec.gta.database.model.dto.UserDTO;
import com.ortec.gta.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
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
    private TokenStore tokenStore;

    @Autowired
    private UserRepositoryImpl userRepository;

    private static final String ROLE_FORMAT = "ROLE_%s";

    /**
     * Do not call for grant or remote roles.
     *
     * @return the model list transformed to an immutable set of GrantedAuthority.
     * This method might be be called to create a new Authentication.
     */
    public ImmutableSet<GrantedAuthority> mapToAppRoles(Set<RoleDTO> roles) {
        return roles.stream()
                .map(x -> new SimpleGrantedAuthority(String.format(ROLE_FORMAT, x.getName().toUpperCase())))
                .collect(ImmutableSet.toImmutableSet());
    }

    /**
     * we check if the user's database roles matches with the session roles.
     * If the user is down or up grade, we force him to re-logging by removing his token.
     */
    public void refreshRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() != null) {
            if (auth.getPrincipal() instanceof TokenedUser) {
                userRepository.findById(((TokenedUser) auth.getPrincipal()).getId()).ifPresent(user -> {
                    if (rolesChanged(user)) {
                        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
                        OAuth2Authentication oauth = tokenStore.readAuthentication(details.getTokenValue());

                        tokenStore.removeAccessToken(tokenStore.getAccessToken(oauth));
                    }
                });
            }
        }
    }

    private boolean rolesChanged(UserDTO user) {
        Collection<? extends GrantedAuthority> databaseRoles = mapToAppRoles(user.getRoles());
        Collection<? extends GrantedAuthority> sessionRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        return databaseRoles.size() != sessionRoles.size() ||
                !(sessionRoles.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())
                        .containsAll(databaseRoles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet())));
    }
}
