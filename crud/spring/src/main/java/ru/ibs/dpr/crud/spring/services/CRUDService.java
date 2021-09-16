package ru.ibs.dpr.crud.spring.services;

import ru.ibs.dpr.crud.spring.dto.ResponseDto;
import ru.ibs.dpr.crud.spring.flk.ValidationContext;
import ru.ibs.dpr.crud.spring.flk.ValidationCondition;

import java.util.Map;

public interface CRUDService<TYPE_ID, DTO, CONDITION extends ValidationCondition<?,?>> {
    DTO read(TYPE_ID id);

    void delete(TYPE_ID id);

    DTO createDto(Map<String, Object> dto);

    DTO create(DTO dto);

    DTO create(DTO dto, ValidationContext<CONDITION> context);

    DTO update(DTO dto, TYPE_ID id);

    DTO update(DTO dto, TYPE_ID id, ValidationContext<CONDITION> context);
}
