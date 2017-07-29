package com.ortec.ihm.clktime.rest.service.authentication;

import com.lambdista.util.Try;
import com.ortec.ihm.clktime.rest.model.dto.GlobalUser;
import com.ortec.ihm.clktime.rest.model.entities.User;
import com.ortec.ihm.clktime.rest.repositories.UserRepository;
import jdk.nashorn.internal.objects.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

        return Optional.ofNullable(
                ldapUser.map(ldap -> userRepository.findByUsername(username)
                            .map((found) -> GlobalUser.of(ldap, found))
                            .orElseGet(() -> {
                                GlobalUser first_auth = GlobalUser.of(ldap);
                                userRepository.save(first_auth.getModel());
                                return first_auth;
                            })
                ).orElseGet(() -> userRepository.findByUsernameAndPassword(username, password)
                        .map((found) -> GlobalUser.of(null, found))
                        .orElse(null)));
    }
}
