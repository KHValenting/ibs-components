package ru.ibs.dpr.crud.spring.flk;

public interface ValidationService<ENTITY, CONDITION extends ValidationCondition<?,?>> {

    ValidationResult<CONDITION> validate(Long domainId, ENTITY target);
}
