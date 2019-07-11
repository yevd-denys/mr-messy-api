package com.mrmessy.messenger.exceptions;

import com.mrmessy.messenger.common.ValidationErrorEntity;

import java.util.List;

public class GraphQLValidationException extends RuntimeException {

    private List<ValidationErrorEntity> errors;

    public GraphQLValidationException() {
        super();
    }

    public GraphQLValidationException(String message) {
        super(message);
    }

    public GraphQLValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphQLValidationException(String message, List<ValidationErrorEntity> errors) {
        super(message);
        this.errors = errors;
    }

    public GraphQLValidationException(List<ValidationErrorEntity> errors) {
        this.errors = errors;
    }

    public List<ValidationErrorEntity> getErrors() {
        return errors;
    }
}
