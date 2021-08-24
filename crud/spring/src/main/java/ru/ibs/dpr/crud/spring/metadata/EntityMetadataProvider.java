package ru.ibs.dpr.crud.spring.metadata;

import java.util.Map;

public interface EntityMetadataProvider {
    Map<String, EntityMetadata<?,?,?>> getEntitiesMap();
}
