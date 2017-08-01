package com.ortec.ihm.clktime.rest.service.authentication;

import com.ortec.ihm.clktime.rest.model.dto.GlobalUser;

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
    Optional<GlobalUser> loadByConnection(String username, String password);
}
