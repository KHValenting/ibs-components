package ru.ibs.dpr.crud.spring.services.interceptors;

public class DefaultPreCreateInterceptorImpl<DTO, ENTITY> implements InterceptorPreCreate<DTO, ENTITY> {
    @Override
    public ENTITY preCreate(ENTITY newEntity, DTO dto) {
        return newEntity;
    }
}
