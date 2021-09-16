package com.example.service;

import com.example.dto.ThirdExampleDto;
import com.example.entities.ThirdExampleEntity;
import com.example.repo.ThirdExampleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ibs.dpr.crud.spring.flk.ValidationCondition;
import ru.ibs.dpr.crud.spring.flk.ValidationService;
import ru.ibs.dpr.crud.spring.mapper.Mapper;
import ru.ibs.dpr.crud.spring.services.AbstractCrudServiceImpl;
import ru.ibs.dpr.crud.spring.services.CRUDService;
import ru.ibs.dpr.crud.spring.services.interceptors.InterceptorPreCreate;
import ru.ibs.dpr.crud.spring.services.interceptors.InterceptorPreUpdate;

public class ThirdExampleEntityServiceImpl
        extends AbstractCrudServiceImpl<Long,
                                        ThirdExampleDto,
                                        ThirdExampleEntity,
                                        ThirdExampleRepository,
                                        Mapper<ThirdExampleDto, ThirdExampleEntity>,
                ValidationCondition<Long, String>>
        implements CRUDService<Long, ThirdExampleDto, ValidationCondition<Long, String>> {


    public ThirdExampleEntityServiceImpl(ThirdExampleRepository repository,
                                         Mapper<ThirdExampleDto, ThirdExampleEntity> mapper,
                                         InterceptorPreCreate<ThirdExampleDto, ThirdExampleEntity> interceptorPreCreate,
                                         InterceptorPreUpdate<ThirdExampleDto, ThirdExampleEntity> interceptorPreUpdate,
                                         ValidationService<ThirdExampleEntity, ValidationCondition<Long, String>> validationService,
                                         ObjectMapper objectMapper) {
        super(repository, mapper, interceptorPreCreate, interceptorPreUpdate, validationService, objectMapper);
    }
}
