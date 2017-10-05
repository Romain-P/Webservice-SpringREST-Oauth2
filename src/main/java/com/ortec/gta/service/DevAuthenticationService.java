package com.ortec.gta.service;

import com.google.common.collect.Sets;
import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.UserDTO;
import fr.ortec.security.auth.common.AuthenticationConfigurer;
import fr.ortec.security.auth.common.OrtecAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 05/10/2017
 */

@Service
public class DevAuthenticationService extends OrtecAuthenticationProvider<UserDTO> {
    @Autowired
    UserRepositoryImpl userRepository;

    @Autowired
    MetaDirectoryService metaDirectory;

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
                });
    }
}
