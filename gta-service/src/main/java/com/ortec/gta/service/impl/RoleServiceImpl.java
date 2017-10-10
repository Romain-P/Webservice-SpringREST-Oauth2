package com.ortec.gta.service.impl;

import com.ortec.gta.database.dao.RoleDAO;
import com.ortec.gta.domain.RoleDTO;
import com.ortec.gta.service.RoleService;
import com.ortec.gta.shared.AbstractCrudService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

/**
 * @Author: romain.pillot
 * @Date: 05/09/2017
 */
@Service
public class RoleServiceImpl extends AbstractCrudService<RoleDTO, RoleDAO> implements RoleService {
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
