package com.ortec.gta.service.authentication;

import com.google.common.collect.Sets;
import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.UserDTO;
import com.ortec.gta.service.MetaDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 13/09/2017
 */
@Service
public class DevAuthenticationService implements AuthenticationService {
    private final UserRepositoryImpl userRepository;
    private final MetaDirectoryService metaDirectory;

    @Autowired
    public DevAuthenticationService(MetaDirectoryService metaDirectory,
                                    UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
        this.metaDirectory = metaDirectory;
    }

    /**
     * performs a connection without care about the password :)
     */
    public Optional<UserDTO> loadByConnection(String username, String password) {
        Optional<UserDTO> databaseUser = userRepository.findByUsername(username);

        return Optional.ofNullable(databaseUser.orElseGet(() -> {
            String[] split = username.split("\\.");

            return metaDirectory.findUserDetails(split[0].toLowerCase(), split[1].toLowerCase())
                    .map(x -> {
                        userRepository.create(x.setId(0)
                                .setSuperior(null)
                                .setChildren(Sets.newHashSet())
                                .setRoles(Sets.newHashSet())
                                .setActivities(Sets.newHashSet()), true);
                        return x;
                    }).orElse(null);
        }));
    }
}
