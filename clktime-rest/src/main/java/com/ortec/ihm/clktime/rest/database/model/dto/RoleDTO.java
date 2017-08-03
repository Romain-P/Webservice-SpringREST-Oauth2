package com.ortec.ihm.clktime.rest.database.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private int id;
    private String name;
}
