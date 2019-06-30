package com.mrmessy.messenger.graphql.schema.queries;

import com.mrmessy.messenger.entities.User;
import com.mrmessy.messenger.graphql.schema.GraphQLContext;
import com.mrmessy.messenger.services.UserService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Component
@SuppressWarnings("unused")
public class UsersQL {

    private final UserService userService;

    public UsersQL(UserService userService) {
        this.userService = userService;
    }

    @GraphQLQuery
    public Optional<User> user(@GraphQLArgument(name = "id") Integer id,
                               @GraphQLArgument(name = "email") String email,
                               @GraphQLEnvironment ResolutionEnvironment env) {
        GraphQLContext context = (GraphQLContext) env.rootContext;

        if (nonNull(id)) {
            return userService.getById(id);
        }
        if (isNotBlank(email)) {
            return userService.getByEmail(email);
        }
        return Optional.of(context.getUser());
    }
}
