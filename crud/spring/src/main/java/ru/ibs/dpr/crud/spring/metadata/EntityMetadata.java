package ru.ibs.dpr.crud.spring.metadata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.ibs.dpr.crud.spring.flk.ValidationCondition;
import ru.ibs.dpr.crud.spring.services.CRUDService;
import ru.ibs.dpr.crud.spring.services.FindService;

@AllArgsConstructor
public class EntityMetadata<TYPE_ID, DTO, CONDITION extends ValidationCondition<?,?>> {
    @Getter
    private final String entityName;
    @Getter
    private final CRUDService<TYPE_ID, DTO, CONDITION> crudService;
    @Getter
    private final FindService<DTO> findService;
}