package com.ortec.gta.database.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;


/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */

@Accessors(chain = true)
@Getter @Setter
public final class UserDTO {
    private Integer id;
    private Integer metaId;
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
}
