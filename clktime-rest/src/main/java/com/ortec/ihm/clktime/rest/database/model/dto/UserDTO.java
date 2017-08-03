package com.ortec.ihm.clktime.rest.database.model.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class UserDTO {
    private Set<RoleDTO> roles;
    private String username;
    private String name;
    private String lastname;
    private byte[] avatar;
    private String email;
}
