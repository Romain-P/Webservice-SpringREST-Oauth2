package com.ortec.gta.database.dao;

import com.ortec.gta.database.entity.User;
import com.ortec.gta.database.repository.UserRepository;
import com.ortec.gta.domain.UserDTO;
import com.ortec.gta.shared.AbstractDAO;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Component("UserDAO")
public class UserDAO extends AbstractDAO<UserRepository, User, UserDTO> {

    public UserDAO() {
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