package com.ortec.gta.service;

import com.ortec.gta.common.service.AbstractCrudService;
import com.ortec.gta.database.RoleRepositoryImpl;
import com.ortec.gta.database.model.dto.RoleDTO;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 05/09/2017
 */
@Service
public class RoleService extends AbstractCrudService<RoleDTO, RoleRepositoryImpl> {
    @Override
    @Secured("ROLE_ADMIN")
    public void create(RoleDTO dto) {
        super.create(dto);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public void delete(RoleDTO dto) {
        super.delete(dto);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public void update(RoleDTO dto) {
        super.update(dto);
    }
}
