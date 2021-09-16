package ru.ibs.dpr.crud.spring.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import ru.ibs.dpr.crud.spring.flk.ValidationCondition;
import ru.ibs.dpr.crud.spring.flk.ValidationContext;
import ru.ibs.dpr.crud.spring.flk.ValidationResult;
import ru.ibs.dpr.crud.spring.flk.ValidationService;
import ru.ibs.dpr.crud.spring.mapper.Mapper;
import ru.ibs.dpr.crud.spring.services.interceptors.InterceptorPreCreate;
import ru.ibs.dpr.crud.spring.services.interceptors.InterceptorPreUpdate;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractCrudServiceImpl<TYPE_ID,
                                              DTO,
                                              ENTITY,
                                              REPOSITORY extends JpaRepository<ENTITY, TYPE_ID>,
                                              MAPPER extends Mapper<DTO, ENTITY>,
                                              CONDITION extends ValidationCondition<?,?>>
        implements CRUDService<TYPE_ID, DTO, CONDITION> {

    private final Class<TYPE_ID> typeIdClass;
    private final Class<DTO> dtoClass;
    private final Class<ENTITY> entityClass;


    {
        final Class<?>[] genericClasses = GenericTypeResolver.resolveTypeArguments(getClass(), AbstractCrudServiceImpl.class);
        Assert.notNull(genericClasses, "Can not resolve generic parameters");
        //noinspection unchecked
        this.typeIdClass = (Class<TYPE_ID>) genericClasses[0];
        //noinspection unchecked
        this.dtoClass = (Class<DTO>) genericClasses[1];
        //noinspection unchecked
        this.entityClass = (Class<ENTITY>) genericClasses[2];
    }

    @Getter(AccessLevel.PROTECTED) private final REPOSITORY repository;

    @Getter(AccessLevel.PROTECTED) private final MAPPER mapper;

    @Getter(AccessLevel.PROTECTED) private final InterceptorPreCreate<DTO, ENTITY> interceptorPreCreate;

    @Getter(AccessLevel.PROTECTED) private final InterceptorPreUpdate<DTO, ENTITY> interceptorPreUpdate;

    @Getter(AccessLevel.PROTECTED) private final ValidationService<ENTITY, CONDITION> validationService;

    @Getter(AccessLevel.PROTECTED) private final ObjectMapper objectMapper;

    public AbstractCrudServiceImpl(REPOSITORY repository,
                                   MAPPER mapper,
                                   InterceptorPreCreate<DTO, ENTITY> interceptorPreCreate,
                                   InterceptorPreUpdate<DTO, ENTITY> interceptorPreUpdate,
                                   ValidationService<ENTITY, CONDITION> validationService,
                                   ObjectMapper objectMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.interceptorPreCreate = interceptorPreCreate;
        this.interceptorPreUpdate = interceptorPreUpdate;
        this.validationService = validationService;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public DTO create(DTO dto) {
        ENTITY newEntity = getMapper().dtoToEntity(dto);
        return create(dto, newEntity);
    }

    @Override
    @Transactional
    public DTO create(DTO dto, ValidationContext<CONDITION> context) {
        ENTITY newEntity = getMapper().dtoToEntity(dto);
        validate(newEntity, context);
        return create(dto, newEntity);
    }

    private DTO create(DTO dto, ENTITY newEntity) {
        final Object entityId = getEntityId(newEntity);
        if (entityId != null) {
            throw new IllegalArgumentException("Попытка создать новую запись с указанным id=" + entityId);
        }
        if (getInterceptorPreCreate() != null) {
            newEntity = getInterceptorPreCreate().preCreate(newEntity, dto);
        }
        newEntity = getRepository().save(newEntity);
        return getMapper().entityToDto(newEntity);
    }



    @Override
    @Transactional(readOnly = true)
    public DTO read(TYPE_ID id) {
        if (isNullId(id)) {
            throw new IllegalArgumentException("Не указан Id");
        }

        TYPE_ID realId = convertIdToTypeId(id);
        final ENTITY found = findById(realId);
        return getMapper().entityToDto(found);
    }



    @Override
    @Transactional
    public DTO update(DTO dto, TYPE_ID id) {
        if (isNullId(id)) {
            throw new IllegalArgumentException("Не указан Id");
        }
        if (dto == null) {
            throw new IllegalArgumentException("Не указан Dto");
        }
        TYPE_ID realId = convertIdToTypeId(id);
        ENTITY curEntity = findById(realId);
        ENTITY newEntity = getMapper().dtoToEntity(dto);
        if (getInterceptorPreUpdate() != null) {
            newEntity = getInterceptorPreUpdate().preUpdate(newEntity, curEntity, dto);
        }
        newEntity = getRepository().save(newEntity);
        return getMapper().entityToDto(newEntity);
    }

    @Override
    @Transactional
    public DTO update(DTO dto, TYPE_ID id, ValidationContext<CONDITION> context) {
        if (isNullId(id)) {
            throw new IllegalArgumentException("Не указан Id");
        }
        ENTITY curEntity = findById(id);
        ENTITY newEntity = getMapper().dtoToEntity(dto);
        validate(newEntity, context);
        if (getInterceptorPreUpdate() != null) {
            newEntity = getInterceptorPreUpdate().preUpdate(newEntity, curEntity, dto);
        }
        newEntity = getRepository().save(newEntity);
        return getMapper().entityToDto(newEntity);
    }



    @Override
    @Transactional
    public void delete(TYPE_ID id) {
        if (isNullId(id)) {
            throw new IllegalArgumentException("Не указан Id");
        }

        TYPE_ID realId = convertIdToTypeId(id);
        getRepository().deleteById(realId);
    }

    private boolean isNullId(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue() <= 0;
        }
        if (value instanceof String) {
            return !StringUtils.hasText((String)value);
        }
        return false;
    }

    protected ENTITY findById(TYPE_ID id) {
        final Optional<ENTITY> optional = getRepository().findById(id);
        optional.orElseThrow(() -> new RuntimeException("Запись не найдена по id=" + id));
        return optional.get();
    }

    private void validate(ENTITY target, ValidationContext<CONDITION> context){
        if (getValidationService() == null) {
                log.debug("Skipping validation due to validation service is not set");
            return;
        }
        var conditionalResult = getValidationService().validate(context.getDomainId(), target);
        context.setValidationResult(conditionalResult);
        if (isContainCriticalErrors(conditionalResult)){
            throw getValidationException(conditionalResult);
        }
    }

    protected boolean isContainCriticalErrors(@SuppressWarnings("unused") ValidationResult<CONDITION> validationResult) {
        return false;
    }

    protected RuntimeException getValidationException(ValidationResult<CONDITION> validationResult) {
        final String errorsMessages = validationResult.getResult().stream()
                .map(ValidationCondition::getErrorMessage)
                .map(Object::toString)
                .collect(Collectors.toList()).toString();
        return new RuntimeException(errorsMessages);
    }

    protected TYPE_ID convertIdToTypeId(TYPE_ID id) {
        if (Objects.equals(id.getClass(), typeIdClass)) {
            return id;
        }

        return getObjectMapper().convertValue(id, typeIdClass);
    }

    @SneakyThrows
    private Object getEntityId(ENTITY entity) {
        final Field[] declaredFields = entityClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                declaredField.setAccessible(true);
                return declaredField.get(entity);
            }
        }
        return false;
    }

    @Override
    public DTO createDto(Map<String, Object> dto) {
        return objectMapper.convertValue(dto, dtoClass);
    }
}
