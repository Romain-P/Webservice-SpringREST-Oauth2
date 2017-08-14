package com.ortec.ihm.clktime.rest.database.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;


/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */

@Accessors(chain = true)
@Getter @Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public final class UserDTO {
    private Integer id;
    private String username;
    private String name;
    private String lastname;
    private byte[] avatar;
    private String email;
    private UserDTO superior;
    private Set<RoleDTO> roles;
    private Set<ActivityDTO> activities;
}
