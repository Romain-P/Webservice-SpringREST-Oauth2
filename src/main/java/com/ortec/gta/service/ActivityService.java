package com.ortec.gta.service;

import com.google.common.collect.Sets;
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
import java.util.stream.Stream;

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
        Set<ActivityDTO> actives = getRepository().findActiveActivities().stream()
                .filter(activity -> activity.getParentActivity() == null)
                .sorted((a, b) -> Long.compare(b.getSubActivities().stream().max(Comparator.comparing(ActivityDTO::getModificationDate)).orElse(b)
                                .getModificationDate(), a.getSubActivities().stream().max(Comparator.comparing(ActivityDTO::getModificationDate)).orElse(a).getModificationDate()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return actives.stream().flatMap(set ->
            Stream.concat(Stream.of(set), set.getSubActivities().stream()
                    .sorted((a, o2) -> Long.compare(o2.getModificationDate(), a.getModificationDate())))
        ).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<ActivityDTO> getParents() {
        return getRepository().findParentActivities();
    }

    public Set<ActivityDTO> getParents(int id) {
        return getRepository().findById(id)
                .map(activity -> getRepository().findChildrenActivities(activity))
                .orElseGet(Sets::newHashSet);
    }

    @Override
    public void update(ActivityDTO dto) {
        getRepository().findById(dto.getId()).ifPresent(stored ->
            updateDto(dto, stored.getCreationDate(), System.currentTimeMillis(), -1));
    }

    @Override
    public void create(ActivityDTO dto) {
        long current = System.currentTimeMillis();
        dto.setActive(true);
        updateDto(dto, current, current, 0);
    }

    @Override
    public void delete(Integer id) {
        getRepository().findById(id).ifPresent(stored -> {
            stored.getSubActivities().forEach(x -> delete(x.getId()));

            long current = System.currentTimeMillis();

            stored.setActive(false);
            updateDto(stored, stored.getCreationDate(), current, current);
        });
    }

    private void updateDto(ActivityDTO dto, long create, long edit, long delete) {
        userService.get(SessionUtil.activeUser().getId()).ifPresent(dto::setLastEditor);

        dto.setCreationDate(create)
                .setModificationDate(edit)
                .setDeletionDate(delete);

        super.update(dto);
    }
}
