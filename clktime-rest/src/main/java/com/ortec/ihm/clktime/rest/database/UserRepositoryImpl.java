package com.ortec.ihm.clktime.rest.database;

import com.ortec.ihm.clktime.rest.database.converter.CrudRepositoryDtoConverter;
import com.ortec.ihm.clktime.rest.database.model.dto.RoleDTO;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.Role;
import com.ortec.ihm.clktime.rest.database.model.entity.User;
import com.ortec.ihm.clktime.rest.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Component("DtoUserRepository")
public class UserRepositoryImpl extends CrudRepositoryDtoConverter<User, UserDTO> {
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

    @Override
    protected void defineConverter(ConverterBuilder<User, UserDTO> builder, Converter converter) {
        builder
                .convertEntity((entity, dto) -> dto.getRoles().addAll(converter.toSet(entity.getRoles(), RoleDTO.class)))
                .convertDto((dto, entity) -> entity.setRoles(converter.toSet(dto.getRoles(), Role.class)));
    }
}