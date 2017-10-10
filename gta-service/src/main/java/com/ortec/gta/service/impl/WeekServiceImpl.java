package com.ortec.gta.service.impl;

import com.ortec.gta.database.dao.WeekDAO;
import com.ortec.gta.domain.WeekDTO;
import com.ortec.gta.service.WeekService;
import com.ortec.gta.shared.AbstractCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 24/08/2017
 */
@Service
public class WeekServiceImpl extends AbstractCrudService<WeekDTO, WeekDAO> implements WeekService {
    @PreAuthorize("principal.id == #userId or hasRole('ROLE_ADMIN') " +
                  "or @securityService.isSuperiorOf(principal.id, #userId)")
    public Set<WeekDTO> getUserWeeks(Long userId, int weekNumber, int year) {
        return getRepository().findByUser(userId, weekNumber, year);
    }

    @PreAuthorize("principal.id == #userId or hasRole('ROLE_ADMIN') " +
            "or @securityService.isSuperiorOf(principal.id, #dto.user.id)")
    public void create(WeekDTO dto) {
        super.create(dto);
    }

    @PreAuthorize("principal.id == #userId or hasRole('ROLE_ADMIN') " +
            "or @securityService.isSuperiorOf(principal.id, #dto.user.id)")
    public void delete(WeekDTO dto) {
        super.delete(dto);
    }

    @PreAuthorize("principal.id == #userId or hasRole('ROLE_ADMIN') " +
            "or @securityService.isSuperiorOf(principal.id, #dto.user.id)")
    public void update(WeekDTO dto) {
        super.update(dto);
    }
}
