package com.ortec.gta.service;

import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.UserDTO;
import fr.ortec.dsi.domaine.Utilisateur;
import fr.ortec.security.auth.common.AuthenticationConfigurer;
import fr.ortec.security.auth.common.OrtecAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: romain.pillot
 * @Date: 05/10/2017
 */

@Service
public class AuthenticationService extends OrtecAuthenticationProvider<UserDTO> {
    @Autowired
    UserRepositoryImpl userRepository;

    @Override
    protected void configure(AuthenticationConfigurer<UserDTO> configurer) {
        configurer
                .loadUserByUsername(username -> userRepository.findByUsername(username))
                .createUserEntity(this::createUserEntity)
                .rescueInvalidAuthentication((user, pass) -> userRepository.findByUsernameAndPassword(user, pass))
                .enableRsaPassword();
    }

    private UserDTO createUserEntity(Utilisateur ldap) {
        UserDTO dto = new UserDTO();
        dto.setUsername(ldap.getUsername());
        dto.setEmail(ldap.getEmail());
        dto.setLastname(ldap.getNom());
        dto.setName(ldap.getPrenom());
        dto.setFixedId(-1L);

        userRepository.create(dto, true);
        return dto;
    }
}
