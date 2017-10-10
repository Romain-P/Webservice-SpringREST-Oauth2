package com.ortec.gta.service;

import com.ortec.gta.domain.UserDTO;
import com.ortec.gta.shared.CrudService;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 10/10/2017
 */
public interface UserService extends CrudService<UserDTO>{
    Optional<UserDTO> getWithMetaCall(Long id);
}
