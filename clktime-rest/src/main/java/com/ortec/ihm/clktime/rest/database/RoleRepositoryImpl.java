package com.ortec.ihm.clktime.rest.database;

import com.ortec.ihm.clktime.rest.database.converter.CrudRepositoryDtoConverter;
import com.ortec.ihm.clktime.rest.database.model.dto.RoleDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.Role;
import com.ortec.ihm.clktime.rest.database.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Component;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Component("DtoRoleRepository")
public class RoleRepositoryImpl extends CrudRepositoryDtoConverter<RoleRepository, Role, RoleDTO> {
    public RoleRepositoryImpl() {
        super(Role.class, RoleDTO.class);
    }
}
