package ru.ibs.dpr.crud.spring.flk;

public interface ValidationCondition<CODE, MESSAGE> {
    CODE getErrorCode();
    MESSAGE getErrorMessage();
}
