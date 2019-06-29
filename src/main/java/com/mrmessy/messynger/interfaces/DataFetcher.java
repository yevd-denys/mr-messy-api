package com.mrmessy.messynger.interfaces;

import graphql.schema.DataFetchingEnvironment;

public interface DataFetcher<T> {
    T get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception;
}