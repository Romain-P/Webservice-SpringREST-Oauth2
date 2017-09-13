package com.ortec.gta.service.authentication;

import com.ortec.gta.database.model.dto.UserDTO;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 27/07/2017
 */
public interface AuthenticationService {
    /**
     * Might be generate a global user from an username and password connection.
     *
     * @param username the username
     * @param password the assigned password
     * @return an optional of global user (content is nullable).
     */
    Optional<UserDTO> loadByConnection(String username, String password);
}
