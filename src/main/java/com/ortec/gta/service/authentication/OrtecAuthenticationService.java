package com.ortec.gta.service.authentication;

import com.lambdista.util.Try;
import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.UserDTO;
import com.ortec.gta.service.MetaDirectoryService;
import fr.ortec.dsi.domaine.Utilisateur;
import fr.ortec.dsi.securite.authentification.activedirectory.ADAuthentification;
import fr.ortec.dsi.securite.authentification.services.Authentification;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 25/07/2017
 */

@Service
public class OrtecAuthenticationService implements AuthenticationService {
    private final Authentification authentication;
    private final UserRepositoryImpl userRepository;
    private final MetaDirectoryService metaDirectory;

    @Autowired
    public OrtecAuthenticationService(MetaDirectoryService metaDirectory,
                                      UserRepositoryImpl userRepository,
                                      @Value("${login-service.url}") String remoteAddress,
                                      @Value("${login-service.domain}") String baseDomain,
                                      @Value("${login-service.authentication-type}") String authenticationType) {
        this.userRepository = userRepository;
        this.authentication = new ADAuthentification(remoteAddress, baseDomain, authenticationType);
        this.metaDirectory = metaDirectory;
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
     * @return a UserDTO and generate its model
     */
    private UserDTO ldapFirstConnection(Utilisateur ldap) {
        UserDTO dto = new UserDTO();
        dto.setUsername(ldap.getUsername());
        dto.setAvatar(ldap.getAvatar());
        dto.setEmail(ldap.getEmail());
        dto.setLastname(ldap.getNom());
        dto.setName(ldap.getPrenom());

        userRepository.create(dto, true);
        return dto;
    }
}
