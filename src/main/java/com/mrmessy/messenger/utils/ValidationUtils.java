package com.mrmessy.messenger.utils;


import com.mrmessy.messenger.exceptions.GraphQLValidationException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;

public class ValidationUtils {

    private ValidationUtils() {
        throw new IllegalArgumentException("ValidationUtils can't be instantiated");
    }

    public static <T> void validateEntity(T entity, Validator validator) {
        Errors errors = new BeanPropertyBindingResult(entity, entity.getClass().getName());
        validator.validate(entity, errors);
        checkValidationErrors(errors);
    }

    public static <T> void validateEntitySmart(T entity, SmartValidator validator, Object[] hints) {
        Errors errors = new BeanPropertyBindingResult(entity, entity.getClass().getName());
        validator.validate(entity, errors, hints);
        checkValidationErrors(errors);
    }

    private static void checkValidationErrors(Errors errors) {
        if (!errors.getAllErrors().isEmpty()) {
            throw new GraphQLValidationException(ErrorUtils.parseValidationErrors(errors.getAllErrors()));
        }
    }
}
