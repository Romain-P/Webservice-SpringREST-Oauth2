package com.ortec.ihm.clktime.rest.database.converter;

import com.google.common.collect.Streams;
import com.ortec.ihm.clktime.rest.util.ReflectUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

/**
 * Might be extended if you need to create an overlay of a CRUDRepository,
 * working with DTO instead of the Entity directly.
 * This class performs conversions and calls methods from a simple CrudRepository (using Entity)
 * required in the constructor.
 *
 * @param <E> Entity
 * @param <D> Dto
 */
@Getter(AccessLevel.PROTECTED)
public abstract class RepositoryDtoConverter<E, D> implements CrudDTO<D> {
    private DTOConverter<E, D> converter;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;
    private final CrudRepository<E, Integer> repository;

    @Autowired
    ModelMapper mapper;

    /**
     * @param repository from spring data implementing CRUD pattern, performing requests with entities.
     * @param entityClass the entity class (same than the generic type)
     * @param dtoClass the dto class (same than the generic type)
     */
    public RepositoryDtoConverter(CrudRepository<E, Integer> repository, Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.repository = repository;
    }

    /**
     * We initialize the converter with a ConverterBuilder.
     * It provides a DSL, so reducing
     */
    @PostConstruct
    public void initializeMapper() {
        ConverterBuilder<E, D> builder = new ConverterBuilder<>();
        Converter converter = new Converter(mapper);

        defineConverter(builder, converter);

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
    }

    /* overlay of Crud starts */

    public Optional<D> findById(int id) {
        return Optional.ofNullable(converter.fromEntity(repository.findOne(id)));
    }

    public List<D> findAll(){
        return Streams.stream(repository.findAll())
                .map(x -> converter.fromEntity(x))
                .collect(Collectors.toList());
    }

    public void create(final D dto, boolean hasId) {
        E entity = converter.fromDto(dto);
        repository.save(entity);

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
     * Provides a DSL and reduces verbose to create a CustomConverterDTO.
     *
     * @param <E> Entity
     * @param <D> Dto
     */
    protected static class ConverterBuilder<E, D> {
        private BiConsumer<D, E> convertDto;
        private BiConsumer<E, D> convertEntity;

        public ConverterBuilder<E, D> convertDto(BiConsumer<D, E> dto) {
            this.convertDto = dto;
            return this;
        }

        public ConverterBuilder<E, D> convertEntity(BiConsumer<E, D> entity) {
            this.convertEntity = entity;
            return this;
        }
    }

    @RequiredArgsConstructor
    protected static class Converter {
        private final ModelMapper mapper;

        /**
         * Create an instance of the class `to` from an instance `from`,
         * Working with matching fields
         *
         * @param from object to convert
         * @param to class generated
         * @param <S> Entity or Dto type
         * @param <T> Dto or Entity type
         * @return
         */
        public <S, T> T convert(S from, Class<T> to) {
            return mapper.map(from, to);
        }
    }

    /**
     * Might be overridden if the convertEntity fields differs from the convertDto ones.
     * Provide default, a SimpleConverterDTO that maps dto <-> entity with a simple model mapper.
     *
     * @return the DTOConverter used by this wrapper.
     */
    protected void defineConverter(ConverterBuilder<E, D> builder, Converter converter) {}
}
