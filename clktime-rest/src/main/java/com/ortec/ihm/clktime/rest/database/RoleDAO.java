package com.ortec.ihm.clktime.rest.database;

import com.ortec.ihm.clktime.rest.database.model.dto.RoleDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.Role;
import org.springframework.stereotype.Component;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */
@Component
public class RoleDAO extends AbstractDTOConverterDAO<Role, RoleDTO> {}
