package com.mrmessy.messenger.graphql.schema.mutations;

import com.mrmessy.messenger.entities.User;
import com.mrmessy.messenger.graphql.dto.UserMutationDto;
import com.mrmessy.messenger.services.UserService;
import com.mrmessy.messenger.utils.ValidationUtils;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.SmartValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersMutationQL {

    private final SmartValidator validator;
    private final UserService userService;

    public UsersMutationQL(SmartValidator validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    @GraphQLMutation
    public User saveUser(@GraphQLNonNull @GraphQLArgument(name = "user") @Valid UserMutationDto dto) {
        ValidationUtils.validateEntity(dto, validator);
        return userService.save(dto);
    }

    @GraphQLMutation
    public User updateUser(@GraphQLNonNull @GraphQLArgument(name = "user") @Valid UserMutationDto dto) {
        ValidationUtils.validateEntitySmart(dto, validator, validationHints());
        return userService.update(dto);
    }

    @GraphQLMutation
    public void deleteUser(@GraphQLNonNull @GraphQLArgument(name = "id") Integer id) {
        userService.delete(id);
    }


    private Class[] validationHints() {
        List<Class> hints = new ArrayList<>();
        hints.add(UserMutationDto.UpdateModeValidation.class);
        return hints.toArray(new Class[hints.size()]);
    }
}
