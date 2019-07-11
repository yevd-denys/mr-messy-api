package com.mrmessy.messenger.validations;

import com.mrmessy.messenger.graphql.dto.UserMutationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, Object> {

    @Override
    public void initialize(PasswordsEqualConstraint arg0) {
    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
        UserMutationDto user = (UserMutationDto) candidate;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
