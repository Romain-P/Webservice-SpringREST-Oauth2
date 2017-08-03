package com.ortec.ihm.clktime.rest.database;

import com.ortec.ihm.clktime.rest.database.converter.CustomConverterDTO;
import com.ortec.ihm.clktime.rest.database.converter.DTOConverter;
import com.ortec.ihm.clktime.rest.database.converter.RepositoryDtoConverter;
import com.ortec.ihm.clktime.rest.database.model.dto.RoleDTO;
import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.Role;
import com.ortec.ihm.clktime.rest.database.model.entity.User;
import com.ortec.ihm.clktime.rest.database.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Component("user")
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

    @Override
    protected DTOConverter<User, UserDTO> defineConverter(ModelMapper mapper, Class<User> user, Class<UserDTO> dto) {
        return new CustomConverterDTO<User, UserDTO>(mapper, user, dto) {
            @Override
            public User fromDto(UserDTO dto) {
                return getEntity(dto).setRoles(dto.getRoles().stream()
                        .map(x -> convert(x, Role.class))
                        .collect(Collectors.toSet()));
            }

            @Override
            public UserDTO fromEntity(User entity) {
                UserDTO dto = getDto(entity);

                dto.getRoles().addAll(entity.getRoles().stream()
                        .map(x -> convert(x, RoleDTO.class))
                        .collect(Collectors.toSet()));
                return dto;
            }
        };
    }
}