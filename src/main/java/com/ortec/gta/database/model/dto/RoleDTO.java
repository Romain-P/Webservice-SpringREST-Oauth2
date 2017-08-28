package com.ortec.gta.database.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Accessors(chain = true)
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public final class RoleDTO {
    private Integer id;
    private String name;
}
