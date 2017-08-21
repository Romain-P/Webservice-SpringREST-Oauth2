package com.ortec.gta.database;

import com.ortec.gta.common.database.CrudRepositoryDtoConverter;
import com.ortec.gta.database.model.entity.Role;
import com.ortec.gta.database.model.dto.RoleDTO;
import com.ortec.gta.database.repository.RoleRepository;
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
