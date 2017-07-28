package com.ortec.ihm.clktime.rest.service.authentication;

import com.lambdista.util.Try;
import com.ortec.ihm.clktime.rest.model.dsl.Users;
import com.ortec.ihm.clktime.rest.model.dto.GlobalUser;
import com.ortec.ihm.clktime.rest.repositories.UserRepository;
import fr.ortec.dsi.domaine.Utilisateur;
import fr.ortec.dsi.securite.authentification.activedirectory.ADAuthentification;
import fr.ortec.dsi.securite.authentification.services.Authentification;
import fr.ortec.dsi.securite.authentification.services.exception.AuthentificationException;
import jdk.nashorn.internal.objects.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 25/07/2017
 */

@Service
public class OrtecAuthenticationService implements AuthenticationService{
    private final Authentification authentication;
    private final UserRepository userRepository;

    @Autowired
    public OrtecAuthenticationService(UserRepository userRepository,
                                      @Value("${login-service.url}") String remoteAddress,
                                      @Value("${login-service.domain}") String baseDomain,
                                      @Value("${login-service.authentication-type}") String authenticationType)
    {
        this.userRepository = userRepository;
        this.authentication = new ADAuthentification(remoteAddress, baseDomain, authenticationType);
    }

    public Optional<GlobalUser> loadByConnection(String username, String password) {
        Optional<Utilisateur> ldapUser = Try.apply(() -> authentication.getUtilisateur(username, password)).toOptional();

        /** todo
        return ldapUser
                .map(x -> Optional.of(GlobalUser.of(ldapUser.get(), userRepository.findByUsername(username))))
                .orElse(GlobalUser.of(null, userRepository.findByUsernamePassword(username, password))); **/
    }
}
