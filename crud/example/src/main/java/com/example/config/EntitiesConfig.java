package com.example.config;

import com.example.dto.AnotherExampleDto;
import com.example.dto.ExampleDto;
import com.example.dto.ThirdExampleDto;
import com.example.entities.AnotherExampleEntity;
import com.example.entities.ExampleEntity;
import com.example.entities.ThirdExampleEntity;
import com.example.repo.AnotherExampleRepository;
import com.example.repo.ExampleRepository;
import com.example.repo.ThirdExampleRepository;
import com.example.service.ThirdExampleEntityServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ibs.dpr.crud.spring.flk.ValidationCondition;
import ru.ibs.dpr.crud.spring.flk.ValidationResult;
import ru.ibs.dpr.crud.spring.flk.ValidationService;
import ru.ibs.dpr.crud.spring.mapper.AbstractMapperImpl;
import ru.ibs.dpr.crud.spring.mapper.Mapper;
import ru.ibs.dpr.crud.spring.metadata.DefaultEntityMetadataProviderImpl;
import ru.ibs.dpr.crud.spring.metadata.EntityMetadata;
import ru.ibs.dpr.crud.spring.metadata.EntityMetadataProvider;
import ru.ibs.dpr.crud.spring.services.AbstractCrudServiceImpl;
import ru.ibs.dpr.crud.spring.services.AbstractFindService;
import ru.ibs.dpr.crud.spring.services.CRUDService;
import ru.ibs.dpr.crud.spring.services.FindService;
import ru.ibs.dpr.crud.spring.services.interceptors.DefaultPreCreateInterceptorImpl;
import ru.ibs.dpr.crud.spring.services.interceptors.DefaultPreUpdateInterceptorImpl;
import ru.ibs.dpr.crud.spring.services.interceptors.InterceptorPreCreate;
import ru.ibs.dpr.crud.spring.services.interceptors.InterceptorPreUpdate;

@Configuration
public class EntitiesConfig {

    @Bean
    public CRUDService<Long, ExampleDto, ValidationCondition<Long, String>> exampleCrudService(ExampleRepository repository,
                                                                                               Mapper<ExampleDto, ExampleEntity> mapper,
                                                                                               @Autowired(required = false)InterceptorPreCreate<ExampleDto, ExampleEntity> preCreateInterceptor,
                                                                                               @Autowired(required = false)InterceptorPreUpdate<ExampleDto, ExampleEntity> preUpdateInterceptor,
                                                                                               @Autowired(required = false) ValidationService<ExampleEntity, ValidationCondition<Long, String>> validationService,
                                                                                               ObjectMapper objectMapper) {

        return new AbstractCrudServiceImpl<>(repository, mapper, preCreateInterceptor, preUpdateInterceptor, validationService, objectMapper){};
    }

    @Bean
    public Mapper<ExampleDto, ExampleEntity> exampleMapper() {
        return new AbstractMapperImpl<>(){};
    }

    @Bean
    public InterceptorPreCreate<ExampleEntity, ExampleDto> examplePreCreateInterceptor() {
        return new DefaultPreCreateInterceptorImpl<>();
    }

    @Bean
    public InterceptorPreUpdate<ExampleEntity, ExampleDto> examplePreUpdateInterceptor() {
        return new DefaultPreUpdateInterceptorImpl<>();
    }

    @Bean
    public ValidationService<?, ValidationCondition<Long, String>> validationService() {
        return (domainId, target) -> ValidationResult.<ValidationCondition<Long, String>>builder().build();
    }

    @Bean
    public FindService<ExampleDto> exampleFindService(ExampleRepository repository,
                                                      Mapper<ExampleDto, ExampleEntity> mapper) {
        return new AbstractFindService<>(repository, mapper) {};
    }







    @Bean
    public CRUDService<Long, AnotherExampleDto, ValidationCondition<Long, String>> anotherExampleCrudService(AnotherExampleRepository repository,
                                                                                                             Mapper<AnotherExampleDto, AnotherExampleEntity> mapper,
                                                                                                             @Autowired(required = false) InterceptorPreCreate<AnotherExampleDto, AnotherExampleEntity> preCreateInterceptor,
                                                                                                             @Autowired(required = false) InterceptorPreUpdate<AnotherExampleDto, AnotherExampleEntity> preUpdateInterceptor,
                                                                                                             @Autowired(required = false) ValidationService<AnotherExampleEntity, ValidationCondition<Long, String>> validationService,
                                                                                                             ObjectMapper objectMapper) {

        return new AbstractCrudServiceImpl<>(repository, mapper, preCreateInterceptor, preUpdateInterceptor, validationService, objectMapper){};
    }

    @Bean
    public Mapper<AnotherExampleDto, AnotherExampleEntity> anotherExampleMapper() {
        return new AbstractMapperImpl<>(){};
    }

    @Bean
    public InterceptorPreCreate<AnotherExampleDto, AnotherExampleEntity> anotherExamplePreCreateInterceptor() {
        return new DefaultPreCreateInterceptorImpl<>();
    }

    @Bean
    public InterceptorPreUpdate<AnotherExampleDto, AnotherExampleEntity> anotherExamplePreUpdateInterceptor() {
        return new DefaultPreUpdateInterceptorImpl<>();
    }

    @Bean
    public FindService<AnotherExampleDto> anotherExampleFindService(AnotherExampleRepository repository,
                                                                    Mapper<AnotherExampleDto, AnotherExampleEntity> mapper) {
        return new AbstractFindService<>(repository, mapper) {};
    }






    @Bean
    public CRUDService<Long, ThirdExampleDto, ValidationCondition<Long, String>> thirdExampleCrudService(ThirdExampleRepository repository,
                                                                                                         Mapper<ThirdExampleDto, ThirdExampleEntity> mapper,
                                                                                                         @Autowired(required = false) InterceptorPreCreate<ThirdExampleDto, ThirdExampleEntity> preCreateInterceptor,
                                                                                                         @Autowired(required = false) InterceptorPreUpdate<ThirdExampleDto, ThirdExampleEntity> preUpdateInterceptor,
                                                                                                         @Autowired(required = false) ValidationService<ThirdExampleEntity, ValidationCondition<Long, String>> validationService,
                                                                                                         ObjectMapper objectMapper) {

        return new ThirdExampleEntityServiceImpl(repository, mapper, preCreateInterceptor, preUpdateInterceptor, validationService, objectMapper);
    }

    @Bean
    public Mapper<ThirdExampleDto, ThirdExampleEntity> thirdExampleMapper() {
        return new AbstractMapperImpl<>(){};
    }

    @Bean
    public InterceptorPreCreate<ThirdExampleDto, ThirdExampleEntity> thirdExamplePreCreateInterceptor() {
        return new DefaultPreCreateInterceptorImpl<>();
    }

    @Bean
    public InterceptorPreUpdate<ThirdExampleDto, ThirdExampleEntity> thirdExamplePreUpdateInterceptor() {
        return new DefaultPreUpdateInterceptorImpl<>();
    }

    @Bean
    public FindService<ThirdExampleDto> thirdExampleFindService(ThirdExampleRepository repository,
                                                                Mapper<ThirdExampleDto, ThirdExampleEntity> mapper) {
        return new AbstractFindService<>(repository, mapper) {};
    }




    @Bean
    public EntityMetadataProvider entityMetadataProvider(ApplicationContext applicationContext) {
        final DefaultEntityMetadataProviderImpl defaultEntityMetadataProvider = new DefaultEntityMetadataProviderImpl();


        final EntityMetadata<Long, ExampleDto, ValidationCondition<Long, String>> entityMetadata
                = new EntityMetadata<>(ExampleEntity.class.getSimpleName(),
                (CRUDService<Long, ExampleDto, ValidationCondition<Long, String>>) applicationContext.getBean("exampleCrudService"),
                (FindService<ExampleDto>) applicationContext.getBean("exampleFindService"));
        defaultEntityMetadataProvider.addEntity(entityMetadata);

        final EntityMetadata<Long, AnotherExampleDto, ValidationCondition<Long, String>> anotherEntityMetadata
                = new EntityMetadata<>(AnotherExampleEntity.class.getSimpleName(),
                (CRUDService<Long, AnotherExampleDto, ValidationCondition<Long, String>>) applicationContext.getBean("anotherExampleCrudService"),
                (FindService<AnotherExampleDto>) applicationContext.getBean("anotherExampleFindService"));
        defaultEntityMetadataProvider.addEntity(anotherEntityMetadata);


        final EntityMetadata<Long, AnotherExampleDto, ValidationCondition<Long, String>> thirdEntityMetadata
                = new EntityMetadata<>(ThirdExampleEntity.class.getSimpleName(),
                (CRUDService<Long, AnotherExampleDto, ValidationCondition<Long, String>>) applicationContext.getBean("thirdExampleCrudService"),
                (FindService<AnotherExampleDto>) applicationContext.getBean("thirdExampleFindService"));
        defaultEntityMetadataProvider.addEntity(thirdEntityMetadata);

        return defaultEntityMetadataProvider;
    }


}
