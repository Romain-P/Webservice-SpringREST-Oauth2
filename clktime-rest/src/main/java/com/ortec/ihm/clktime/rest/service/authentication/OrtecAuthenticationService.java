package com.ortec.ihm.clktime.rest.service.authentication;

import com.lambdista.util.Try;
import com.ortec.ihm.clktime.rest.model.dto.GlobalUser;
import com.ortec.ihm.clktime.rest.model.entities.User;
import com.ortec.ihm.clktime.rest.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public OrtecAuthenticationService(UserRepository userRepository,
                                      @Value("${login-service.url}") String remoteAddress,
                                      @Value("${login-service.domain}") String baseDomain,
                                      @Value("${login-service.authentication-type}") String authenticationType)
    {
        this.userRepository = userRepository;
        this.authentication = new Authentification(remoteAddress, baseDomain, authenticationType);
    }

    public Optional<GlobalUser> loadByConnection(String username, String password) {
        Optional<Utilisateur> ldapUser = Try.apply(() -> authentication.getUtilisateur(username, password)).toOptional();

        return ldapUser.map(ldap -> {
                    User user = userRepository.findByUsername(username);

                    if (user == null) {
                        Optional<GlobalUser> first_auth = Optional.of(GlobalUser.of(ldap));
                        userRepository.save(first_auth.get().getModel());
                        return first_auth;
                    }

                    return GlobalUser.of(ldap, user);
                })
                .orElseGet(() -> GlobalUser.of(null, userRepository.findByUsernameAndPassword(username, password)));
    }
}
