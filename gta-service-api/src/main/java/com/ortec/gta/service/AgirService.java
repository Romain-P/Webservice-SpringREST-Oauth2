package com.ortec.gta.service;

import com.ortec.gta.domain.AbsenceDayDTO;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/10/2017
 */
public interface AgirService {
    Set<AbsenceDayDTO> getAbsenceDays(Long userId, int year);
}
