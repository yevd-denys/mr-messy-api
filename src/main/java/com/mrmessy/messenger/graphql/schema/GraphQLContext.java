package com.mrmessy.messenger.graphql.schema;

import com.mrmessy.messenger.entities.User;

public class GraphQLContext {

    private final User user;

    public GraphQLContext(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
