package com.ortec.gta.service.authentication;

import com.google.common.collect.Sets;
import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.UserDTO;
import com.ortec.gta.service.MetaDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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
        })).map(x -> {
            Set<UserDTO> metaUsers = metaDirectory.getUserChildren(x);

            if (metaUsers.size() > 0)
                persistChildren(x.setChildren(metaUsers));
            return x;
        });
    }

    /**
     * Retrieves the children of a given parent user,
     * and then persists them into the database
     *
     * @param parent user parent
     */
    private void persistChildren(UserDTO parent) {
        for (UserDTO child : parent.getChildren()) {
            UserDTO user = userRepository.findByUsername(getUsername(child))
                    .map(x -> x.setSuperior(parent))
                    .orElseGet(() -> child.setId(0).setSuperior(null));

            if (user.getId() == 0)
                userRepository.create(user, true);
            else
                userRepository.update(user);
        }
    }

    /**
     * @param dto user to convert
     * @return the username of a given user dto
     */
    private String getUsername(UserDTO dto) {
        return String.format("%s.%s", dto.getName(), dto.getLastname()).toLowerCase();
    }
}
