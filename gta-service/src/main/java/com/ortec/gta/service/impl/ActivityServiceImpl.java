package com.ortec.gta.service.impl;

import com.google.common.collect.Sets;
import com.ortec.gta.database.dao.ActivityDAO;
import com.ortec.gta.domain.ActivityDTO;
import com.ortec.gta.service.ActivityService;
import com.ortec.gta.service.UserService;
import com.ortec.gta.shared.AbstractCrudService;
import com.ortec.gta.shared.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
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
public class ActivityServiceImpl extends AbstractCrudService<ActivityDTO, ActivityDAO> implements ActivityService {

    @Autowired
    private UserService userService;

    /**
     * Unused in the new version (on angular).
     * This method loaded all activities and sorted it by modification date of children, packing their parent
     * and associated children. see: https://stackoverflow.com/questions/45818320/java-8-advanced-sort
     */
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
        return getRepository().findParentActivities().stream()
                .sorted(Comparator.comparing(ActivityDTO::getModificationDate).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<ActivityDTO> getChildren(Long id) {
        return getRepository().findById(id)
                .map(activity -> getRepository().findChildrenActivities(activity).stream()
                        .sorted(Comparator.comparing(ActivityDTO::getModificationDate).reversed())
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .orElseGet(Sets::newLinkedHashSet);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public void update(ActivityDTO dto) {
        getRepository().findById(dto.getId()).ifPresent(stored ->
            updateDto(dto, stored.getCreationDate(), System.currentTimeMillis(), -1, false));
    }

    @Override
    @Secured("ROLE_ADMIN")
    public void create(ActivityDTO dto) {
        long current = System.currentTimeMillis();
        dto.setActive(true);
        updateDto(dto, current, current, -1, true);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public void delete(ActivityDTO stored) {
        stored.getSubActivities().forEach(this::delete);

        long current = System.currentTimeMillis();

        stored.setActive(false);
        updateDto(stored, stored.getCreationDate(), current, current, false);
    }

    private void updateDto(ActivityDTO dto, long creation, long edit, long delete, boolean create) {
        userService.get(SessionUtil.activeUser().getId()).ifPresent(dto::setLastEditor);

        dto.setCreationDate(creation)
                .setModificationDate(edit)
                .setDeletionDate(delete);

        if (create)
            super.create(dto);
        else
            super.update(dto);
    }
}
