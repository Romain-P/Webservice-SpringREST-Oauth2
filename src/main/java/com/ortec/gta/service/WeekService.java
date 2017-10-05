package com.ortec.gta.service;

import com.ortec.gta.common.service.AbstractCrudService;
import com.ortec.gta.database.WeekRepositoryImpl;
import com.ortec.gta.database.model.dto.WeekDTO;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 24/08/2017
 */
@Service
public class WeekService extends AbstractCrudService<WeekDTO, WeekRepositoryImpl> {
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
