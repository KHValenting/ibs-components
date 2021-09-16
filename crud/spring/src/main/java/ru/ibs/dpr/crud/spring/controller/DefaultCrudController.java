package ru.ibs.dpr.crud.spring.controller;

import org.springframework.web.bind.annotation.*;
import ru.ibs.dpr.crud.spring.metadata.EntityMetadata;
import ru.ibs.dpr.crud.spring.metadata.EntityMetadataProvider;
import ru.ibs.dpr.crud.spring.services.CRUDService;

import java.util.Map;

@RestController
@RequestMapping("${ru.ibs.dpr.crud.endpoint:crud}" + "/**")
public class DefaultCrudController {

    private final EntityMetadataProvider entityMetadataProvider;

    public DefaultCrudController(EntityMetadataProvider entityMetadataProvider) {
        this.entityMetadataProvider = entityMetadataProvider;
    }

    @PutMapping("{name}")
    public <DTO> DTO create(@PathVariable String name, @RequestBody Map<String, Object> dto) {
        final EntityMetadata<?, DTO, ?> entityMetadata = (EntityMetadata<?, DTO, ?>) entityMetadataProvider.getEntitiesMap().get(name);
        final CRUDService<?, DTO, ?> crudService = entityMetadata.getCrudService();
        return crudService.create(crudService.createDto(dto));
    }

    @GetMapping("{name}/{id}")
    public <TYPE_ID> Object read(@PathVariable String name, @PathVariable TYPE_ID id) {
        final EntityMetadata<TYPE_ID, ?, ?> entityMetadata = (EntityMetadata<TYPE_ID, ?, ?>) entityMetadataProvider.getEntitiesMap().get(name);
        final CRUDService<TYPE_ID, ?, ?> crudService = entityMetadata.getCrudService();
        return crudService.read(id);
    }

    @PostMapping("{name}/{id}")
    public <TYPE_ID, DTO> Object update(@PathVariable String name, @PathVariable TYPE_ID id, @RequestBody Map<String, Object> dto) {
        final EntityMetadata<TYPE_ID, DTO, ?> entityMetadata = (EntityMetadata<TYPE_ID, DTO, ?>) entityMetadataProvider.getEntitiesMap().get(name);
        final CRUDService<TYPE_ID, DTO, ?> crudService = entityMetadata.getCrudService();
        return crudService.update(crudService.createDto(dto), id);
    }

    @DeleteMapping("{name}/{id}")
    public <TYPE_ID> void delete(@PathVariable String name, @PathVariable TYPE_ID id) {
        final EntityMetadata<TYPE_ID, ?, ?> entityMetadata = (EntityMetadata<TYPE_ID, ?, ?>) entityMetadataProvider.getEntitiesMap().get(name);
        final CRUDService<TYPE_ID, ?, ?> crudService = entityMetadata.getCrudService();
        crudService.delete(id);
    }
}