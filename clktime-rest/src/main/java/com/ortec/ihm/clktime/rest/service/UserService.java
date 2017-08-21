package com.ortec.ihm.clktime.rest.service;

import com.ortec.ihm.clktime.rest.common.service.AbstractCrudService;
import com.ortec.ihm.clktime.rest.database.UserRepositoryImpl;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import org.springframework.stereotype.Service;

/**
 * @Author: romain.pillot
 * @Date: 18/08/2017
 */
@Service
public class UserService extends AbstractCrudService<UserDTO, UserRepositoryImpl> {}
