package com.ortec.ihm.clktime.rest.database.converter;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */
public interface DTOConverter<E, D> {
    E fromDto(D dto);
    D fromEntity(E entity);
}
