package ru.ibs.dpr.crud.tests.tests.services.interceptors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ibs.dpr.crud.spring.services.interceptors.DefaultPreCreateInterceptorImpl;
import ru.ibs.dpr.crud.tests.testdata.entity.TestEntity;
import ru.ibs.dpr.crud.tests.testdata.model.TestDto;

class DefaultPreCreateInterceptorImplTest {

    @Test
    void preCreateTest() {
        TestEntity testEntity = new TestEntity(1L, "TestData", 100000L);
        TestDto testDto = new TestDto(1L, "TestData", 100000L);
        DefaultPreCreateInterceptorImpl<TestDto, TestEntity> pci = new DefaultPreCreateInterceptorImpl<>();
        TestEntity returnedEntity = pci.preCreate(testEntity, testDto);
        Assertions.assertNotNull(returnedEntity);
        Assertions.assertEquals(testEntity, returnedEntity);
        Assertions.assertEquals("TestData", returnedEntity.getExampleFieldOne());
    }
}