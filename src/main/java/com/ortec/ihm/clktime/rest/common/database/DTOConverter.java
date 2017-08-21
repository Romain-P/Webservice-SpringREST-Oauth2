package com.ortec.ihm.clktime.rest.common.database;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

import java.util.Collection;
import java.util.Set;

/**
 * A DTOConverter is supposed to generate conversions
 * between Entity <-> Dto.
 *
 * @param <E> Entity
 * @param <D> Dto
 */
public interface DTOConverter<E, D> {
    /**
     * @param dto to convert
     * @return an entity from the DTO
     */
    E fromDto(D dto);
    Set<E> fromDto(Collection<D> dto);

    /**
     * @param entity to convert
     * @return a DTO from the entity
     */
    D fromEntity(E entity);
    Set<D> fromEntity(Collection<E> entity);
}
