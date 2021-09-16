package ru.ibs.dpr.crud.spring.services;

import ru.ibs.dpr.crud.spring.dto.ResponseDto;
import ru.ibs.dpr.crud.spring.params.Params;

public interface FindService<DTO> {
    <PARAMS extends Params> ResponseDto<DTO> find(PARAMS params);
}