package com.ortec.ihm.clktime.rest.database.model.dto;

import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */

@Accessors(chain = true)
@Data
public final class UserDTO {
    private final Set<RoleDTO> roles = Sets.newHashSet();
    private Integer id;
    private String username;
    private String name;
    private String lastname;
    private byte[] avatar;
    private String email;
}
