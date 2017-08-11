package com.ortec.ihm.clktime.rest.database.model.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Accessors(chain = true)
@Getter @Setter
public final class RoleDTO {
    private Integer id;
    private String name;
}
