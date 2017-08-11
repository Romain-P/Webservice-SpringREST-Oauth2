package com.ortec.ihm.clktime.rest.database;

import com.ortec.ihm.clktime.rest.database.converter.CrudRepositoryDtoConverter;
import com.ortec.ihm.clktime.rest.database.model.dto.ActivityDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.Activity;
import com.ortec.ihm.clktime.rest.database.repository.ActivityRepository;
import org.springframework.stereotype.Component;

/**
 * @Author: romain.pillot
 * @Date: 10/08/2017
 */
@Component("DtoActivityRepository")
public class ActivityRepositoryImpl extends CrudRepositoryDtoConverter<ActivityRepository, Activity, ActivityDTO> {
    public ActivityRepositoryImpl() {
        super(Activity.class, ActivityDTO.class);
    }
}
