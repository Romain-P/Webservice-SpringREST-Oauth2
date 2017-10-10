package com.ortec.gta.core;

import com.google.common.collect.Sets;
import com.ortec.gta.database.dao.UserDAO;
import com.ortec.gta.domain.UserDTO;
import com.ortec.gta.service.impl.MetaDirectoryServiceImpl;
import fr.ortec.security.auth.common.AuthenticationConfigurer;
import fr.ortec.security.auth.common.OrtecAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 05/10/2017
 */

public class DevAuthenticationConfiguration extends OrtecAuthenticationProvider<UserDTO> {
    @Autowired
    UserDAO userRepository;

    @Autowired
    MetaDirectoryServiceImpl metaDirectory;

    @Override
    protected void configure(AuthenticationConfigurer<UserDTO> configurer) {
        configurer
                .loadUserByUsername(username -> Optional.empty())
                .createUserEntity(ldap -> null)
                .rescueInvalidAuthentication((username, pass) -> {
                    Optional<UserDTO> databaseUser = userRepository.findByUsername(username);

                    return Optional.ofNullable(databaseUser.orElseGet(() -> {
                        String[] split = username.split("\\.");

                        return metaDirectory.findUserDetails(split[0].toLowerCase(), split[1].toLowerCase())
                                .map(x -> {
                                    userRepository.create(x.setId(0L)
                                            .setSuperior(null)
                                            .setChildren(Sets.newHashSet())
                                            .setRoleObjects(Sets.newHashSet())
                                            .setFixedId(-1L)
                                            .setActivities(Sets.newHashSet()), true);
                                    return x;
                                }).orElse(null);
                    }));
                })
                .enableRsaPassword();
    }
}
