package com.ortec.gta.service;

import com.ortec.gta.common.service.AbstractCrudService;
import com.ortec.gta.database.ActivityRepositoryImpl;
import com.ortec.gta.database.model.dto.ActivityDTO;
import com.ortec.gta.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
