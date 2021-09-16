package ru.ibs.dpr.crud.spring.services.interceptors;

public interface InterceptorPreUpdate<DTO, ENTITY> {

    ENTITY preUpdate(ENTITY newEntity, ENTITY curEntity, DTO dto);

}