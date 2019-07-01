package com.mrmessy.messenger.graphql.config;

import com.mrmessy.messenger.graphql.schema.queries.UsersQL;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class Query {
    private final UsersQL usersQL;

    @GraphQLQuery(name = "users")
    public UsersQL getUsersQL() {
        return usersQL;
    }

}
