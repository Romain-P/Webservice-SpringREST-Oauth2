package com.ortec.gta.service;

import com.ortec.gta.common.service.AbstractCrudService;
import com.ortec.gta.database.RoleRepositoryImpl;
import com.ortec.gta.database.model.dto.RoleDTO;
import org.springframework.stereotype.Service;

/**
 * @Author: romain.pillot
 * @Date: 05/09/2017
 */
@Service
public class RoleService extends AbstractCrudService<RoleDTO, RoleRepositoryImpl> {}
