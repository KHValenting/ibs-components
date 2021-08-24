package ru.ibs.dpr.crud.spring.services.interceptors;

public class DefaultPreUpdateInterceptorImpl<DTO, ENTITY> implements InterceptorPreUpdate<DTO, ENTITY> {
    @Override
    public ENTITY preUpdate(ENTITY newEntity, ENTITY curEntity, DTO dto) {
        return newEntity;
    }
}
