package com.mrmessy.messenger.utils;

import com.mrmessy.messenger.common.ValidationErrorEntity;
import org.springframework.validation.ObjectError;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ErrorUtils {

    private ErrorUtils() {
        throw new IllegalArgumentException("ErrorUtils can't be instantiated");
    }

    public static List<ValidationErrorEntity> parseValidationErrors(List<ObjectError> errors) {
        return errors.stream().map(e -> ValidationErrorEntity.builder()
                .field(e.getObjectName())
                .message(e.getDefaultMessage())
                .build())
                .collect(toList());
    }

    public static <T, U> void checkEntityNotFound(T entity, U entityId, String entityName) {
        if (entity == null) {
            throw new EntityNotFoundException(String.format("%s with %s not found", entityName, String.valueOf(entityId)));
        }
    }

    public static <T, U> void checkEntitiesWrongSize(Collection<T> first, Collection<U> second, String entityName) {
        if (first.size() != second.size()) {
            throw new EntityNotFoundException(String.format("Entity %s has missed ids %s, found ids are %s",
                    entityName, first, second));
        }
    }
}
