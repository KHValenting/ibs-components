package ru.ibs.dpr.crud.tests.testdata.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.ibs.dpr.crud.tests.testdata.entity.TestEntity;

public interface MockRepo extends JpaRepository<TestEntity, Long>,
                                  JpaSpecificationExecutor<TestEntity> {
}
