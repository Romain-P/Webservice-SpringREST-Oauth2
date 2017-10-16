package com.ortec.gta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.stream.Collectors;


/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */

@Accessors(chain = true)
@Getter @Setter
public final class UserDTO implements UserIdentity {
    private Long id;
    private Long fixedId;
    private String username;
    private String name;
    private String lastname;
    private String email;
    @JsonIgnoreProperties(value = {"activities"})
    private UserDTO superior;
    private Set<RoleDTO> roles;
    @JsonIgnoreProperties(value = {"users", "parentActivity"})
    private Set<ActivityDTO> activities;
    @JsonIgnoreProperties(value = {"superior"})
    private Set<UserDTO> children;

    /**
     * Call it if you know what you do.
     *
     * @return the roles set formatted for the Spring Session.
     */
    public Set<String> getSessionRoles() {
        return this.roles.stream()
                .map(RoleDTO::getName)
                .collect(Collectors.toSet());
    }
}
