package com.ortec.gta.core;

import com.ortec.gta.database.dao.UserDAO;
import com.ortec.gta.domain.UserDTO;
import fr.ortec.dsi.domaine.Utilisateur;
import fr.ortec.security.auth.common.AuthenticationConfigurer;
import fr.ortec.security.auth.common.OrtecAuthenticationProvider;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author: romain.pillot
 * @Date: 05/10/2017
 */

@Component
public class AuthenticationConfiguration extends OrtecAuthenticationProvider<UserDTO> {
    @Autowired
    UserDAO userRepository;

    @Override
    protected void configure(AuthenticationConfigurer<UserDTO> configurer) {
        configurer
                .loadUserByUsername(username -> userRepository.findByUsername(username))
                .createUserEntity(this::createUserEntity)
                .rescueInvalidAuthentication((user, pass) -> userRepository.findByLogin(user, DigestUtils.sha256Hex(pass)))
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
