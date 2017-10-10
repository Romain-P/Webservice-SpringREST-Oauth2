package com.ortec.gta.service;

import com.ortec.gta.domain.WeekDTO;
import com.ortec.gta.shared.CrudService;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/10/2017
 */
public interface WeekService extends CrudService<WeekDTO> {
    Set<WeekDTO> getUserWeeks(Long userId, int weekNumber, int year);
}
