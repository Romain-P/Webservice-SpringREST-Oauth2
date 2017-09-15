package com.ortec.gta.service;

import com.ortec.gta.common.service.AbstractCrudService;
import com.ortec.gta.database.UserRepositoryImpl;
import com.ortec.gta.database.model.dto.RoleDTO;
import com.ortec.gta.database.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 18/08/2017
 */
@Service
public class UserService extends AbstractCrudService<UserDTO, UserRepositoryImpl> {

    @Autowired
    private MetaDirectoryService metaDirectory;

    @Override
    public Optional<UserDTO> get(Integer id) {
        return super.get(id).flatMap(x -> {
            Set<UserDTO> metaUsers = metaDirectory.getUserChildren(x);

            for (UserDTO child : metaUsers) {
                UserDTO user = getRepository().findByUsername(getUsername(child))
                        .orElseGet(() -> child.setId(0));

                if (user.getId() == 0)
                    getRepository().create(user, true);
                else if (user.getSuperior() == null || !user.getSuperior().getId().equals(x.getId()))
                    getRepository().update(user.setSuperior(x));
            }

            return super.get(id);
        });
    }

    private String getUsername(UserDTO dto) {
        String name = dto.getName();

        if (name.contains("-") || name.contains(" "))
            name = Arrays.stream(name.split("[-\\s]"))
                    .map(x -> String.valueOf(x.charAt(0)))
                    .collect(Collectors.joining());

        return String.format("%s.%s", name, dto.getLastname()).toLowerCase();
    }

    @Secured("ROLE_ADMIN")
    public void create(UserDTO dto) {
        super.create(dto);
    }

    @PreAuthorize("principal.id == #dto.id or hasRole('ROLE_ADMIN') " +
            "or @securityService.isSuperiorOf(principal.id, #dto.id)")
    public void delete(UserDTO dto) {
        super.delete(dto);
    }

    @PreAuthorize("principal.id == #dto.id or hasRole('ROLE_ADMIN') " +
            "or @securityService.isSuperiorOf(principal.id, #dto.id)")
    public void update(UserDTO dto) {
        get(dto.getId()).ifPresent(x -> {
            dto.setChildren(x.getChildren());
            dto.setSuperior(x.getSuperior());

            super.update(dto);
        });
    }
}
