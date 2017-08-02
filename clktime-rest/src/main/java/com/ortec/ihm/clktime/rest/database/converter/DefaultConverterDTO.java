package com.ortec.ihm.clktime.rest.database.converter;

import com.ortec.ihm.clktime.rest.util.GenericUtil;
import org.modelmapper.ModelMapper;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */
public class DefaultConverterDTO<E, D> implements DTOConverter<E, D> {
    protected final ModelMapper mapper;

    protected static final int INDEX_ENTITY = 0;
    protected static final int INDEX_DTO = 1;

    public DefaultConverterDTO(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public E fromDto(D dto) {
        return mapper.map(dto, GenericUtil.getGenericType(getClass(), INDEX_ENTITY));
    }

    public D fromEntity(E entity) {
        return mapper.map(entity, GenericUtil.getGenericType(getClass(), INDEX_DTO));
    }
}
