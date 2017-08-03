package com.ortec.ihm.clktime.rest.database.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Accessors(chain = true)
@Data
public final class RoleDTO {
    private Integer id;
    private String name;
}
