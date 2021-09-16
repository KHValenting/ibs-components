package ru.ibs.dpr.crud.spring.services.interceptors;

public interface InterceptorPreCreate<DTO, ENTITY> {

    ENTITY preCreate(ENTITY newEntity, DTO dto);

}