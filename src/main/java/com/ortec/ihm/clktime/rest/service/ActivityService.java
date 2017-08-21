package com.ortec.ihm.clktime.rest.service;

import com.ortec.ihm.clktime.rest.common.service.AbstractCrudService;
import com.ortec.ihm.clktime.rest.common.user.TokenedUser;
import com.ortec.ihm.clktime.rest.configuration.annotation.Tokened;
import com.ortec.ihm.clktime.rest.database.ActivityRepositoryImpl;
import com.ortec.ihm.clktime.rest.database.model.dto.ActivityDTO;
import com.ortec.ihm.clktime.rest.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 18/08/2017
 */
@Service
public class ActivityService extends AbstractCrudService<ActivityDTO, ActivityRepositoryImpl> {

    @Autowired
    private UserService userService;

    @Override
    public Set<ActivityDTO> get() {
        return getRepository().findByActiveTrue().stream()
                .sorted(Comparator.comparing(ActivityDTO::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void update(ActivityDTO dto) {
        getRepository().findById(dto.getId()).ifPresent(stored ->
            super.update(updateDto(dto, stored.getCreationDate(), System.currentTimeMillis(), -1)));
    }

    @Override
    public void create(ActivityDTO dto) {
        long current = System.currentTimeMillis();
        dto.setActive(true);
        super.create(updateDto(dto, current, current, 0));
    }

    @Override
    public void delete(Integer id) {
        getRepository().findById(id).ifPresent(stored -> {
            stored.getSubActivities().forEach(x -> delete(x.getId()));

            long current = System.currentTimeMillis();

            stored.setActive(false);
            super.update(updateDto(stored, stored.getCreationDate(), current, current));
        });
    }

    private ActivityDTO updateDto(ActivityDTO dto, long create, long edit, long delete) {
        userService.get(SessionUtil.activeUser().getId()).ifPresent(dto::setLastEditor);

        return dto.setCreationDate(create)
                .setModificationDate(edit)
                .setDeletionDate(delete);
    }
}
