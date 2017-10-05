package com.ortec.gta.database.model.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Accessors(chain = true)
@Getter @Setter
public final class RoleDTO {
    private Long id;
    private String name;
}
