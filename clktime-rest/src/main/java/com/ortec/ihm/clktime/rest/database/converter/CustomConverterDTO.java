package com.ortec.ihm.clktime.rest.database.converter;

import org.modelmapper.ModelMapper;

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
     * @return an associated entity from the dto
     */
    public abstract E fromDto(D dto);

    /**
     * @param entity to convert
     * @return an associated dto from the entity
     */
    public abstract D fromEntity(E entity);

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
}
