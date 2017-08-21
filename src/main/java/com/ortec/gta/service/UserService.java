package com.ortec.gta.service;

import com.ortec.gta.common.service.AbstractCrudService;
import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.UserDTO;
import org.springframework.stereotype.Service;

/**
 * @Author: romain.pillot
 * @Date: 18/08/2017
 */
@Service
public class UserService extends AbstractCrudService<UserDTO, UserRepositoryImpl> {}
