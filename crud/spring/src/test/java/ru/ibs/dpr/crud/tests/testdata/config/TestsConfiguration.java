package ru.ibs.dpr.crud.tests.testdata.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import ru.ibs.dpr.crud.spring.flk.ValidationCondition;
import ru.ibs.dpr.crud.spring.flk.ValidationService;
import ru.ibs.dpr.crud.spring.mapper.AbstractMapperImpl;
import ru.ibs.dpr.crud.spring.mapper.Mapper;
import ru.ibs.dpr.crud.spring.services.AbstractCrudServiceImpl;
import ru.ibs.dpr.crud.spring.services.AbstractFindService;
import ru.ibs.dpr.crud.spring.services.CRUDService;
import ru.ibs.dpr.crud.spring.services.FindService;
import ru.ibs.dpr.crud.spring.services.interceptors.InterceptorPreCreate;
import ru.ibs.dpr.crud.spring.services.interceptors.InterceptorPreUpdate;
import ru.ibs.dpr.crud.tests.testdata.entity.TestEntity;
import ru.ibs.dpr.crud.tests.testdata.model.TestDto;
import ru.ibs.dpr.crud.tests.testdata.repo.MockRepo;
import ru.ibs.dpr.crud.tests.testdata.repo.MockRepoImpl;

@SpringBootConfiguration
@ComponentScan("ru.ibs.dpr.crud.tests")
public class TestsConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MockRepo mockRepo() {
        return new MockRepoImpl();
    }

    @Bean
    public CRUDService<Long, TestDto, ValidationCondition<Long, String>> crudService(MockRepo repository,
                                                                                     Mapper<TestDto, TestEntity> mapper,
                                                                                     @Autowired(required = false) InterceptorPreCreate<TestDto, TestEntity> preCreateInterceptor,
                                                                                     @Autowired(required = false) InterceptorPreUpdate<TestDto, TestEntity> preUpdateInterceptor,
                                                                                     @Autowired(required = false) ValidationService<TestEntity, ValidationCondition<Long, String>> validationService,
                                                                                     ObjectMapper objectMapper) {

        return new AbstractCrudServiceImpl<>(repository, mapper, preCreateInterceptor, preUpdateInterceptor, validationService, objectMapper){};
    }

    @Bean
    public FindService<TestDto> findService(MockRepo repository,
                                            Mapper<TestDto, TestEntity> mapper) {
        return new AbstractFindService<>(repository, mapper) {};
    }


    @Bean
    public Mapper<TestDto, TestEntity> mapper() {
        return new AbstractMapperImpl<>(){};
    }
}
