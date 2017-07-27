package com.ortec.ihm.clktime.rest.service.authentication;

import com.ortec.ihm.clktime.rest.model.dto.User;
import fr.ortec.dsi.securite.authentification.activedirectory.ADAuthentification;
import fr.ortec.dsi.securite.authentification.services.Authentification;
import fr.ortec.dsi.securite.authentification.services.exception.AuthentificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 25/07/2017
 */

@Service
public class OrtecAuthenticationService implements AuthenticationService{
    private final Authentification authentication;

    @Autowired
    public OrtecAuthenticationService(@Value("${login-service.url}") String remoteAddress,
                                      @Value("${login-service.domain}") String baseDomain,
                                      @Value("${login-service.authentication-type}") String authenticationType)
    {
        this.authentication = new ADAuthentification(remoteAddress, baseDomain, authenticationType);
    }

    public Optional<User> loadByConnection(String username, String password) {
        try {
            return Optional.ofNullable(User.create(authentication.getUtilisateur(username, password)));
        } catch (AuthentificationException e) {
            return Optional.empty();
        }
    }
}
