package com.mrmessy.messynger.components;

import com.mrmessy.messynger.repos.UserRepo;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GraphQLDataFetchers {

    private final UserRepo userRepo;

    public GraphQLDataFetchers(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public DataFetcher getUserDataFetcher() {
        return dataFetchingEnvironment -> {
            Integer userId = dataFetchingEnvironment.getArgument("id");
            return userRepo.findById(userId);
        };
    }
}
