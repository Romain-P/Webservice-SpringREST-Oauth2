package com.ortec.gta.service;

import com.ortec.gta.domain.ActivityDTO;
import com.ortec.gta.shared.CrudService;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/10/2017
 */
public interface ActivityService extends CrudService<ActivityDTO> {
    Set<ActivityDTO> getParents();
    Set<ActivityDTO> getChildren(Long id);
}
