package com.ortec.gta.database;

import com.ortec.gta.common.database.CrudRepositoryDtoConverter;
import com.ortec.gta.database.model.dto.WeekDTO;
import com.ortec.gta.database.model.entity.Week;
import com.ortec.gta.database.repository.WeekRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 24/08/2017
 */

@Component("DtoWeekRepository")
public class WeekRepositoryImpl extends CrudRepositoryDtoConverter<WeekRepository, Week, WeekDTO> {
    public WeekRepositoryImpl() {
        super(Week.class, WeekDTO.class);
    }

    public Set<WeekDTO> findByUser(int id) {
        return getConverter().fromEntity(getRepository().findByUserId(id));
    }
}
