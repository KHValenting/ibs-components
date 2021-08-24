package ru.ibs.dpr.crud.spring.controller;

import org.springframework.web.bind.annotation.*;
import ru.ibs.dpr.crud.spring.dto.ResponseDto;
import ru.ibs.dpr.crud.spring.metadata.EntityMetadata;
import ru.ibs.dpr.crud.spring.metadata.EntityMetadataProvider;
import ru.ibs.dpr.crud.spring.params.Params;
import ru.ibs.dpr.crud.spring.services.FindService;

@RestController
@RequestMapping("${ru.ibs.dpr.crud.find.endpoint:find}" + "/**")
public class DefaultFindController {

    private final EntityMetadataProvider entityMetadataProvider;

    public DefaultFindController(EntityMetadataProvider entityMetadataProvider) {
        this.entityMetadataProvider = entityMetadataProvider;
    }

    @GetMapping("{name}")
    public ResponseDto<?> findGet(@PathVariable String name, Params params) {
        return find(name, params);
    }

    @PostMapping("{name}")
    public ResponseDto<?> findPost(@PathVariable String name,  @RequestBody Params params) {
        return find(name, params);
    }

    private ResponseDto<?> find(String name, Params params) {
        final EntityMetadata<?, ?, ?> entityMetadata = entityMetadataProvider.getEntitiesMap().get(name);
        final FindService<?> findService = entityMetadata.getFindService();
        return findService.find(params);
    }
}
