package com.ortec.gta.common.database;

import com.google.common.collect.Streams;
import com.ortec.gta.util.ReflectUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import java.io.Console;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

/**
 * Might be extended if you need to create an overlay of a CRUDRepository,
 * working with DTO instead of the Entity directly.
 * This class performs conversions and calls methods from a simple CrudRepository (using Entity) given as generic <R>
 *
 * @param <R> Repository interface
 * @param <E> Entity
 * @param <D> Dto
 */
@Getter(AccessLevel.PROTECTED)
public abstract class CrudRepositoryDtoConverter<R extends CrudRepository<E, Integer>, E, D> implements CrudDTO<D> {
    private DTOConverter<E, D> converter;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    @Autowired
    ModelMapper mapper;

    @Autowired
    @Qualifier("dtoConverters")
    Map<Class<?>, DTOConverter<?, ?>> converters;

    @Autowired
    R repository;

    /**
     * @param entityClass the entity class (same than the generic type)
     * @param dtoClass the dto class (same than the generic type)
     */
    public CrudRepositoryDtoConverter(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    /**
     * We initialize the converter with a ConverterBuilder.
     * It provides a DSL, so reducing the verbosity
     */
    @PostConstruct
    public void initializeMapper() {
        ConverterBuilder<E, D> builder = new ConverterBuilder<>();
        Converters converter = new Converters(converters);

        this.converter = new CustomConverterDTO<E, D>(mapper, entityClass, dtoClass) {
            @Override
            public E fromDto(D dto) {
                E converted = getEntity(dto);

                if (builder.convertDto != null)
                    builder.convertDto.accept(dto, converted);

                return converted;
            }

            @Override
            public D fromEntity(E entity) {
                D converted = getDto(entity);

                if (builder.convertEntity != null)
                    builder.convertEntity.accept(entity, converted);

                return converted;
            }
        };
        this.converters.put(entityClass, this.converter);
        defineConverter(builder, converter);
    }

    /* overlay of Crud starts */

    public Optional<D> findById(int id) {
        E entity = repository.findOne(id);
        return Optional.ofNullable(entity != null ? converter.fromEntity(repository.findOne(id)) : null);
    }

    public Set<D> findAll(){
        return Streams.stream(repository.findAll())
                .map(x -> converter.fromEntity(x))
                .collect(Collectors.toSet());
    }

    public void create(final D dto, boolean hasId) {
        E entity = converter.fromDto(dto);
        entity = repository.save(entity);
        if (!hasId) return;

        /* Used reflection to don't add multiple verbose interfaces to convertDto/entities */
        ReflectUtil.get(entity, "getId")
                .ifPresent(id -> ReflectUtil.set(dto, "setId", id));
    }

    public void update(D dto){
        repository.save(converter.fromDto(dto));
    }

    public void delete(D dto){
        repository.delete(converter.fromDto(dto));
    }

    public void deleteById(int id){
        repository.delete(id);
    }

    /* ends */

    /**
     * Provides a DSL and reduces verbosity to create a CustomConverterDTO.
     *
     * @param <E> Entity
     * @param <D> Dto
     */
    protected static class ConverterBuilder<E, D> {
        private BiConsumer<D, E> convertDto;
        private BiConsumer<E, D> convertEntity;

        /**
         * @param consumer a biconsumer (dto, entity). `entity` is a basic conversion of `dto`.
         *            Do whatever you want on the entity to continue a custom conversion.
         */
        public ConverterBuilder<E, D> convertDto(BiConsumer<D, E> consumer) {
            this.convertDto = consumer;
            return this;
        }

        ModelMapper mapper;


        /**
         * @param consumer a biconsumer (entity, dto). `dto` is a basic conversion of `entity`.
         *            Do whatever you want on the dto to continue a custom conversion.
         */
        public ConverterBuilder<E, D> convertEntity(BiConsumer<E, D> consumer) {
            this.convertEntity = consumer;
            return this;
        }
    }

    @RequiredArgsConstructor
    protected static class Converters {
        private final Map<Class<?>, DTOConverter<?, ?>> converters;

        @SuppressWarnings("unchecked")
        public <E, D> DTOConverter<E, D> of(Class<E> entity, Class<D> dto) {
            return (DTOConverter<E, D>) converters.get(entity.getClass());
        }
    }

    /**
     * Might be overridden if the convertEntity fields differs from the convertDto ones.
     * By default, a simple conversion will be applied with the model mapper.
     *
     */
    protected void defineConverter(ConverterBuilder<E, D> builder, Converters converters) {}
}
