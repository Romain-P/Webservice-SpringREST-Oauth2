package com.ortec.gta.database;

import com.ortec.gta.common.database.CrudRepositoryDtoConverter;
import com.ortec.gta.database.model.dto.UserDTO;
import com.ortec.gta.database.model.entity.User;
import com.ortec.gta.database.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Component("DtoUserRepository")
public class UserRepositoryImpl extends CrudRepositoryDtoConverter<UserRepository, User, UserDTO> {

    public UserRepositoryImpl() {
        super(User.class, UserDTO.class);
    }

    public Optional<UserDTO> findByUsername(String username) {
        return getRepository().findByUsername(username)
                .map(x -> getConverter().fromEntity(x));
    }

    public Optional<UserDTO> findByLogin(String username, String password) {
        return getRepository().findByUsernameAndPassword(username, password)
                .map(x -> getConverter().fromEntity(x));
    }
}