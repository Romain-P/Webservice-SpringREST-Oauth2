package com.ortec.gta.database;

import com.ortec.gta.common.database.CrudRepositoryDtoConverter;
import com.ortec.gta.database.model.dto.ActivityDTO;
import com.ortec.gta.database.model.entity.Activity;
import com.ortec.gta.database.repository.ActivityRepository;
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

    public Set<ActivityDTO> findActiveActivities() {
        return getConverter().fromEntity(getRepository().findByActiveTrue());
    }

    public Set<ActivityDTO> findParentActivities() {
        return getConverter().fromEntity(getRepository().findByParentActivityIsNullAndActiveTrue());
    }

    public Set<ActivityDTO> findChildrenActivities(ActivityDTO dto) {
        Activity parent = getConverter().fromDto(dto);
        return getConverter().fromEntity(getRepository().findByParentActivityAndActiveTrue(parent));
    }

    @Override
    protected void defineConverter(ConverterBuilder<Activity, ActivityDTO> builder, Converters converters) {
        builder.convertDto((dto, entity) -> {
            if (entity.getDeletionDate().getTime() <= 0)
                entity.setDeletionDate(null);
        });
    }
}
