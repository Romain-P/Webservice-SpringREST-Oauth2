package com.ortec.gta.database.dao;

import com.ortec.gta.database.entity.Week;
import com.ortec.gta.database.repository.WeekRepository;
import com.ortec.gta.domain.WeekDTO;
import com.ortec.gta.shared.AbstractDAO;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 24/08/2017
 */

@Component("WeekDAO")
public class WeekDAO extends AbstractDAO<WeekRepository, Week, WeekDTO> {
    public WeekDAO() {
        super(Week.class, WeekDTO.class);
    }

    public Set<WeekDTO> findByUser(Long id, int weekNumber, int year) {
        return getConverter().fromEntity(getRepository().findByUserId(id, weekNumber, year));
    }
}
