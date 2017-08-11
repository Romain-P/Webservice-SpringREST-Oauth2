package com.ortec.ihm.clktime.rest.database;

import com.ortec.ihm.clktime.rest.database.converter.CrudRepositoryDtoConverter;
import com.ortec.ihm.clktime.rest.database.model.dto.ActivityDTO;
import com.ortec.ihm.clktime.rest.database.model.dto.RoleDTO;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.Activity;
import com.ortec.ihm.clktime.rest.database.model.entity.Role;
import com.ortec.ihm.clktime.rest.database.model.entity.User;
import com.ortec.ihm.clktime.rest.database.repository.UserRepository;
import org.springframework.data.repository.cdi.Eager;
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

    public Optional<UserDTO> findByUsernameAndPassword(String username, String password) {
        return getRepository().findByUsernameAndPassword(username, password)
                .map(x -> getConverter().fromEntity(x));
    }
}