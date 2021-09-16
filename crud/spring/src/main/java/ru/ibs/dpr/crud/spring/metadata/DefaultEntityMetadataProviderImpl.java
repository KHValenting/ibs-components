package ru.ibs.dpr.crud.spring.metadata;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultEntityMetadataProviderImpl
        implements EntityMetadataProvider {

    @Getter
    Map<String, EntityMetadata<?, ?, ?>> entitiesMap = new ConcurrentHashMap<>();

    public void addEntity(EntityMetadata<?, ?, ?> entityMetadata) {
        getEntitiesMap().put(entityMetadata.getEntityName(), entityMetadata);
    }
}
