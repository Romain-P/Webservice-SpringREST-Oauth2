package com.ortec.gta.service;

import com.google.common.collect.ImmutableSet;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/10/2017
 */
public interface UserRoleService {
    ImmutableSet<GrantedAuthority> mapToAppRoles(Set<String> roles);
    void refreshRoles();
}
