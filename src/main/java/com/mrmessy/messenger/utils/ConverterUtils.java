package com.mrmessy.messenger.utils;

import com.mrmessy.messenger.interfaces.Identifiable;
import org.apache.commons.lang3.reflect.TypeUtils;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ConverterUtils {

    public static <E extends Enum<E> & Identifiable> E findById(Integer key, Class<E> cls) {
        return Stream.of(cls.getEnumConstants())
                .filter(e -> e.getId().equals(key))
                .findFirst()
                .orElse(cls.getEnumConstants()[0]);
    }

    public static <E extends Enum<E> & Identifiable> E findById(E[] values, Integer id) {
        return findById(values, id, null);
    }

    public static <E extends Enum<E> & Identifiable> E findById(E[] values, Integer id, E defaultValue) {
        return Stream.of(values).filter(e -> e.getId().equals(id)).findAny().orElse(defaultValue);
    }

    public static <T> T convertToObject(Object value, Class<T> klazz, Supplier<String> errorMessage) {
        if (!TypeUtils.isInstance(value, klazz)) throw new IllegalArgumentException(errorMessage.get());
        return klazz.cast(value);
    }
}
