package com.ortec.ihm.clktime.rest.database.converter;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 03/08/2017
 */

/**
 * Extend this class to create a custom dto converter.
 * Might be extended in case of particular dto fields to set
 * (that is to say if your entity differs from the dto)
 */
public abstract class CustomConverterDTO<E, D> implements DTOConverter<E, D> {
    private final ModelMapper mapper;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    public CustomConverterDTO(ModelMapper mapper, Class<E> entityClass, Class<D> dtoClass) {
        this.mapper = mapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    /**
     * @param dto to convert
     * @return the associated converted entity from the dto.
     *         Matching fields are sets by model mapping
     */
    protected final E getEntity(D dto) {
        return mapper.map(dto, entityClass);
    }

    /**
     * @param entity to convert
     * @return the associated converted dto from the entity.
     *         Matching fields are sets by model mapping
     */
    protected final D getDto(E entity) {
        return mapper.map(entity, dtoClass);
    }

    public final Set<D> fromEntity(Collection<E> from) {
        return from.stream().map(this::fromEntity).collect(Collectors.toSet());
    }

    public final Set<E> fromDto(Collection<D> from) {
        return from.stream().map(this::fromDto).collect(Collectors.toSet());
    }
}
