package com.mrmessy.messenger.interfaces;


import io.leangen.graphql.annotations.GraphQLQuery;

public interface Identifiable {
    @GraphQLQuery(name = "id")
    Integer getId();
}
