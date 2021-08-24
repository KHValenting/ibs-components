package ru.ibs.dpr.crud.spring.mapper;

import lombok.SneakyThrows;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractMapperImpl<DTO, ENTITY> implements Mapper<DTO, ENTITY> {

    private final Class<DTO> dtoClass;
    private final Class<ENTITY> entityClass;

    {
        final Class<?>[] genericClasses = GenericTypeResolver.resolveTypeArguments(getClass(), Mapper.class);
        Assert.notNull(genericClasses, "Can not resolve generic parameters");
        //noinspection unchecked
        this.dtoClass = (Class<DTO>) genericClasses[0];
        //noinspection unchecked
        this.entityClass = (Class<ENTITY>) genericClasses[1];
    }

    @Override
    public ENTITY dtoToEntity(DTO source) {
        if (Objects.equals(entityClass, dtoClass)) {
            return (ENTITY) source;
        }

        return map(source, entityClass);
    }

    public DTO entityToDto(ENTITY source) {
        if (Objects.equals(dtoClass, entityClass)) {
            return (DTO) source;
        }
        return map(source, dtoClass);
    }

    @SneakyThrows
    private <SOURCE, DESTINATION> DESTINATION map(SOURCE source, Class<DESTINATION> destinationClass) {
        final Map<String, Method> sourceGetterMethods = Arrays.stream(source.getClass().getMethods())
                                                        .filter(method -> method.getParameterCount() == 0
                                                                && method.getName().length() > 3
                                                                && method.getName().startsWith("get"))
                                                        .collect(Collectors.toMap(Method::getName, method -> method));
        final Map<String, Method> destinationSetterMethods = Arrays.stream(destinationClass.getMethods())
                                                             .filter(method -> method.getParameterCount() == 1
                                                                     && method.getName().length() > 3
                                                                     && method.getName().startsWith("set"))
                                                             .filter(method -> {
                                                                 final String getterName = "get" + method.getName().substring(3);
                                                                 return sourceGetterMethods.containsKey(getterName)
                                                                         && Objects.equals(method.getParameterTypes()[0], sourceGetterMethods.get(getterName).getReturnType());
                                                             })
                                                             .collect(Collectors.toMap(Method::getName, method -> method));

        final DESTINATION destination = destinationClass.getDeclaredConstructor().newInstance();

        for (Map.Entry<String, Method> entry : destinationSetterMethods.entrySet()) {
            String destinationName = entry.getKey();
            Method destinationMethod = entry.getValue();
            String sourceName = "get" + destinationName.substring(3);
            Method sourceMethod = sourceGetterMethods.get(sourceName);

            final Object sourceValue = sourceMethod.invoke(source);
            destinationMethod.invoke(destination, sourceValue);
        }

        return destination;
    }
}
