package ru.ibs.dpr.crud.tests.tests.services.interceptors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ibs.dpr.crud.spring.services.interceptors.DefaultPreUpdateInterceptorImpl;
import ru.ibs.dpr.crud.tests.testdata.entity.TestEntity;
import ru.ibs.dpr.crud.tests.testdata.model.TestDto;

class DefaultPreUpdateInterceptorImplTest {

    @Test
    void preUpdateTest() {
            TestEntity testEntity = new TestEntity(1L, "TestData", 100000L);
            TestEntity testEntity2 = new TestEntity(2L, "TestData-2", 200000L);
            TestDto testDto = new TestDto(1L, "TestData", 100000L);
            DefaultPreUpdateInterceptorImpl<TestDto, TestEntity> pui = new DefaultPreUpdateInterceptorImpl<>();
            TestEntity returnedEntity = pui.preUpdate(testEntity, testEntity2, testDto);
            Assertions.assertNotNull(returnedEntity);
            Assertions.assertEquals(testEntity, returnedEntity);
            Assertions.assertEquals("TestData", returnedEntity.getExampleFieldOne());
        }

    }
