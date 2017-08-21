package com.ortec.ihm.clktime.rest.database;

import com.ortec.ihm.clktime.rest.common.database.CrudRepositoryDtoConverter;
import com.ortec.ihm.clktime.rest.database.model.dto.ActivityDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.Activity;
import com.ortec.ihm.clktime.rest.database.repository.ActivityRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/08/2017
 */
@Component("DtoActivityRepository")
public class ActivityRepositoryImpl extends CrudRepositoryDtoConverter<ActivityRepository, Activity, ActivityDTO> {
    public ActivityRepositoryImpl() {
        super(Activity.class, ActivityDTO.class);
    }

    public Set<ActivityDTO> findByActiveTrue() {
        return getConverter().fromEntity(getRepository().findByActiveTrue());
    }

    @Override
    protected void defineConverter(ConverterBuilder<Activity, ActivityDTO> builder, Converters converters) {
        builder.convertDto((dto, entity) -> {
            if (entity.getDeletionDate().getTime() <= 0)
                entity.setDeletionDate(null);
        });
    }
}
