package com.ortec.ihm.clktime.rest.database.converter;

import org.modelmapper.ModelMapper;

/**
 * @Author: romain.pillot
 * @Date: 03/08/2017
 */
public abstract class CustomConverterDTO<E, D> extends SimpleConverterDTO<E, D>{
    public CustomConverterDTO(ModelMapper mapper, Class<E> entityClass, Class<D> dtoClass) {
        super(mapper, entityClass, dtoClass);
    }

    public abstract E fromDto(D dto);
    public abstract D fromEntity(E entity);

    protected final E getEntity(D dto) {
        return super.fromDto(dto);
    }

    protected final D getDto(E entity) {
        return super.fromEntity(entity);
    }

    protected <S, T> T convert(S dto, Class<T> to) {
        return mapper().map(dto, to);
    }
}
