package ru.ibs.dpr.crud.spring.mapper;

public interface Mapper<DTO, ENTITY> {

    ENTITY dtoToEntity(DTO source);

    DTO entityToDto(ENTITY source);

}