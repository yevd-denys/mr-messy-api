package com.mrmessy.messenger.interfaces;

import io.leangen.graphql.annotations.GraphQLQuery;
import org.apache.commons.text.WordUtils;

public interface INameable extends Identifiable {

    @GraphQLQuery(name = "name")
    default String getName() {
        return WordUtils.capitalizeFully(name().replace("_", " "));
    }

    String name();
}
