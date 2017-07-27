package com.ortec.ihm.clktime.rest.service.authentication;

import com.ortec.ihm.clktime.rest.model.dto.User;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 27/07/2017
 */
public interface AuthenticationService {
    Optional<User> loadByConnection(String username, String password);
}
