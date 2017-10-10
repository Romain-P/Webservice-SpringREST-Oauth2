package com.ortec.gta.service.impl;

import com.google.common.collect.Sets;
import com.ortec.gta.database.dao.UserDAO;
import com.ortec.gta.domain.UserDTO;
import com.ortec.gta.service.MetaDirectoryService;
import com.ortec.gta.service.UserService;
import com.ortec.gta.shared.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
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
public class UserServiceImpl extends AbstractCrudService<UserDTO, UserDAO> implements UserService {

    @Autowired
    private MetaDirectoryService metaDirectory;

    @PreAuthorize("principal.id == #id or hasRole('ROLE_ADMIN') " +
            "or @securityService.isSuperiorOf(principal.id, #id)")
    public Optional<UserDTO> getWithMetaCall(Long id) {
        return super.get(id).map(x -> {
            Set<UserDTO> metaUsers = metaDirectory.findUserDetails(x)
                    .map(meta -> {
                        if (x.getFixedId() == -1)
                            getRepository().update(x.setFixedId(meta.getId()));

                        return meta.getChildren();
                    }).orElseGet(Sets::newHashSet);

            boolean changes = false;

            for (UserDTO child : metaUsers) {
                String username = getUsername(child);

                UserDTO user = x.getChildren().stream()
                        .filter(y -> y.getUsername().equals(username))
                        .findFirst().orElseGet(() -> getRepository()
                                .findByUsername(getUsername(child))
                                .orElseGet(() -> child.setFixedId(child.getId()).setId(0L).setSuperior(x)));

                if (user.getId() == 0) {
                    getRepository().create(user, true);
                    changes = true;
                } else if (user.getSuperior() == null || !user.getSuperior().getId().equals(x.getId())) {
                    getRepository().update(user.setSuperior(x));
                    changes = true;
                }
            }

            return changes ? super.get(id).get() : x;
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
