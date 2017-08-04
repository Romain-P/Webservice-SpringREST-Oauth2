package com.ortec.ihm.clktime.rest.service.authentication;

import com.lambdista.util.Try;
import com.ortec.ihm.clktime.rest.database.UserRepositoryImpl;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import fr.ortec.dsi.domaine.Utilisateur;
import fr.ortec.dsi.securite.authentification.activedirectory.ADAuthentification;
import fr.ortec.dsi.securite.authentification.services.Authentification;
import org.apache.commons.codec.digest.DigestUtils;
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
    private final UserRepositoryImpl userRepository;

    @Autowired
    public OrtecAuthenticationService(UserRepositoryImpl userRepository,
                                      @Value("${login-service.url}") String remoteAddress,
                                      @Value("${login-service.domain}") String baseDomain,
                                      @Value("${login-service.authentication-type}") String authenticationType)
    {
        this.userRepository = userRepository;
        this.authentication = new ADAuthentification(remoteAddress, baseDomain, authenticationType);
    }

    /**
     * This implementation first check if the user is a ldap user (Ortec Active Directory).
     * If yes, it gonna try to retrieve his app-related details, or create some on the first connection.
     * If not, it checks for a valid couple of username/password(hashed in sha256) and retrieve his details.
     * In case of no account found, the optional will content a null user.
     *
     * @param username the username
     * @param password the assigned password
     * @return an optional of global user.
     */
    public Optional<UserDTO> loadByConnection(String username, String password) {
        Optional<Utilisateur> ldapUser = Try.apply(() -> authentication.getUtilisateur(username, password)).toOptional();

        return Optional.ofNullable(
                ldapUser.map(ldap -> userRepository.findByUsername(username)
                            .orElseGet(() -> ldapFirstConnection(ldap)))
                        .orElseGet(() -> userRepository.findByUsernameAndPassword(username, DigestUtils.sha256Hex(password))
                        .orElse(null)));
    }

    /**
     * @param ldap an ortec active directory user.
     * @return a UserDTO an generate its model
     */
    private UserDTO ldapFirstConnection(Utilisateur ldap) {
        UserDTO first_auth = new UserDTO();
        first_auth.setUsername(ldap.getUsername());
        first_auth.setAvatar(ldap.getAvatar());
        first_auth.setEmail(ldap.getEmail());
        first_auth.setLastname(ldap.getNom());
        first_auth.setName(ldap.getPrenom());

        //TODO: replace with consumer
        userRepository.create(first_auth, true);
        return first_auth;
    }
}
