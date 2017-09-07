package com.ortec.gta.service;

import com.ortec.gta.common.service.AbstractCrudService;
import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.RoleDTO;
import com.ortec.gta.database.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 18/08/2017
 */
@Service
public class UserService extends AbstractCrudService<UserDTO, UserRepositoryImpl> {

    @PreAuthorize("principal.id == #dto.id or hasRole('ROLE_ADMIN')")
    public void create(UserDTO dto) {
        super.create(dto);
    }

    @PreAuthorize("principal.id == #dto.id or hasRole('ROLE_ADMIN')")
    public void delete(UserDTO dto) {
        super.delete(dto);
    }

    @PreAuthorize("principal.id == #dto.id or hasRole('ROLE_ADMIN')")
    public void update(UserDTO dto) {
        super.update(dto);
    }
}
