package com.ortec.gta.service;

import com.ortec.gta.domain.UserDTO;

import java.util.Optional;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/10/2017
 */
public interface MetaDirectoryService {
    Optional<UserDTO> findUserDetails(UserDTO user);
    @Deprecated
    Optional<UserDTO> findUserDetails(String name, String lastname);
    Set<UserDTO> getUserChildren(UserDTO user);
    Optional<UserDTO> getUserParent(UserDTO user);
}
