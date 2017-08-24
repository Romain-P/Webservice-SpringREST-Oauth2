package com.ortec.gta.service;

import com.ortec.gta.common.service.AbstractCrudService;
import com.ortec.gta.database.WeekRepositoryImpl;
import com.ortec.gta.database.model.dto.WeekDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 24/08/2017
 */
@Service
public class WeekService extends AbstractCrudService<WeekDTO, WeekRepositoryImpl> {
    public Set<WeekDTO> getUserWeeks(int userId) {
        return getRepository().findByUser(userId);
    }
}
