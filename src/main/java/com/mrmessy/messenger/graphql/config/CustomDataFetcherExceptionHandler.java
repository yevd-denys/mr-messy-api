package com.mrmessy.messenger.graphql.config;

import com.mrmessy.messenger.common.ValidationErrorEntity;
import com.mrmessy.messenger.exceptions.GraphQLValidationException;
import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.execution.ExecutionPath;
import graphql.language.SourceLocation;

import java.util.List;

public class CustomDataFetcherExceptionHandler implements DataFetcherExceptionHandler {

    private <T extends Exception> T parseStackTrace(Throwable t) {
        if (t == null) {
            return null;
        }

        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }

        if (t instanceof GraphQLValidationException) {
            return (T) t;
        } else {
            return parseStackTrace(t.getCause());
        }
    }

    @Override
    public DataFetcherExceptionHandlerResult onException(DataFetcherExceptionHandlerParameters handlerParameters) {
        Throwable exception = handlerParameters.getException();
        SourceLocation sourceLocation = handlerParameters.getSourceLocation();
        ExecutionPath path = handlerParameters.getPath();

        ExceptionWhileDataFetching error = new ExceptionWhileDataFetching(path, exception, sourceLocation);

        return DataFetcherExceptionHandlerResult.newResult().error(error).build();
    }


    private static class ValidationGraphQLError implements GraphQLError {
        private ValidationErrorEntity validation;

        ValidationGraphQLError(ValidationErrorEntity validation) {
            this.validation = validation;
        }

        @SuppressWarnings("unused")
        public String getField() {
            return validation.getField();
        }

        @Override
        public String getMessage() {
            return validation.getMessage();
        }

        @Override
        public ErrorType getErrorType() {
            return ErrorType.ValidationError;
        }

        @Override
        public List<SourceLocation> getLocations() {
            return null;
        }
    }
}
