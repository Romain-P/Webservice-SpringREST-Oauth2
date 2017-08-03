package com.ortec.ihm.clktime.rest.database;

import com.ortec.ihm.clktime.rest.database.converter.RepositoryDtoConverter;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.User;
import com.ortec.ihm.clktime.rest.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Repository("user")
public class UserRepositoryImpl extends RepositoryDtoConverter<User, UserDTO> {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryImpl(UserRepository userRepository) {
        super(userRepository, User.class, UserDTO.class);
        this.userRepository = userRepository;
    }

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(x -> getConverter().fromEntity(x));
    }

    public Optional<UserDTO> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .map(x -> getConverter().fromEntity(x));
    }
}