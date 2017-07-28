package com.ortec.ihm.clktime.rest.service.authentication;

import com.ortec.ihm.clktime.rest.model.dto.GlobalUser;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 27/07/2017
 */
public interface AuthenticationService {
    Optional<GlobalUser> loadByConnection(String username, String password);
}
