package com.ortec.gta.database.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.ortec.security.auth.common.UserIdentity;
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
    private Set<RoleDTO> roleObjects;
    @JsonIgnoreProperties(value = {"users", "parentActivity"})
    private Set<ActivityDTO> activities;
    @JsonIgnoreProperties(value = {"superior"})
    private Set<UserDTO> children;

    /**
     * Call it if you know what you do.
     *
     * @return the roles set formatted for the Spring Session.
     */
    public Set<String> getRoles() {
        return this.roleObjects.stream()
                .map(RoleDTO::getName)
                .collect(Collectors.toSet());
    }
}
