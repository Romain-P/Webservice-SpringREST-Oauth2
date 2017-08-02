package com.ortec.ihm.clktime.rest.database;

import com.ortec.ihm.clktime.rest.database.converter.DTOConverter;
import com.ortec.ihm.clktime.rest.database.converter.DefaultConverterDTO;
import com.ortec.ihm.clktime.rest.util.GenericUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import java.util.List;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */
public abstract class AbstractDTOConverterDAO<E, D> implements DAO<D> {
    private final DTOConverter<E, D> converter;

    private static final int INDEX_ENTITY = 0;
    private static final int INDEX_DTO = 1;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ModelMapper mapper;

    public AbstractDTOConverterDAO() {
        this.converter = defineConverter();
    }

    public D findById(long id) {
        return converter.fromEntity(entityManager.find(((Class<E>)GenericUtil.getGenericType(getClass(), INDEX_ENTITY)),id ));
    }

    public List<D> findAll(){
        return entityManager
                .createQuery( "from " + ((Class<E>)GenericUtil.getGenericType(getClass(), INDEX_ENTITY))
                        .getAnnotation(Table.class).name())
                .getResultList();
    }

    public void create(D dto){
        entityManager.persist(converter.fromDto(dto));
    }

    public void update(D dto){
        entityManager.merge(converter.fromDto(dto));
    }

    public void delete(D dto){
        entityManager.remove(converter.fromDto(dto));
    }

    public void deleteById(long entityId){
        E entity = entityManager.find(((Class<E>)GenericUtil.getGenericType(getClass(), INDEX_ENTITY)), entityId);
        entityManager.remove(entity);
    }

    protected DTOConverter<E, D> defineConverter() {
        return new DefaultConverterDTO<>(mapper);
    }
}
