package com.ortec.gta.service;

import com.google.common.collect.Sets;
import com.ortec.gta.database.model.dto.StatDTO;
import org.springframework.stereotype.Service;

/**
 * @Author: romain.pillot
 * @Date: 14/09/2017
 */

@Service
public class StatService {
    public StatDTO getStatistics() {
        StatDTO.ActivityStatDTO activity = new StatDTO.ActivityStatDTO("GTA", 10);

        return new StatDTO(1000,
                1000,
                1000,
                Sets.newHashSet(activity),
                Sets.newHashSet(activity));
    }
}
