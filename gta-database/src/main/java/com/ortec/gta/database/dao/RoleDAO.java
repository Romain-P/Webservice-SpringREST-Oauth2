package com.ortec.gta.database.dao;

import com.ortec.gta.database.entity.Role;
import com.ortec.gta.database.repository.RoleRepository;
import com.ortec.gta.domain.RoleDTO;
import com.ortec.gta.shared.AbstractDAO;
import org.springframework.stereotype.Component;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Component("RoleDAO")
public class RoleDAO extends AbstractDAO<RoleRepository, Role, RoleDTO> {
    public RoleDAO() {
        super(Role.class, RoleDTO.class);
    }
}
