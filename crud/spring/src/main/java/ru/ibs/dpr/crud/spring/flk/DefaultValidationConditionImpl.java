package ru.ibs.dpr.crud.spring.flk;

import lombok.Getter;

public class DefaultValidationConditionImpl
        implements ValidationCondition<Long, String> {

    @Getter private Long errorCode;
    @Getter private String errorMessage;
}
