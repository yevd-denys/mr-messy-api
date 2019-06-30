package com.mrmessy.messenger.graphql.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrmessy.messenger.config.ApplicationConfiguration;
import com.mrmessy.messenger.graphql.types.CustomEnumMapper;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.generator.mapping.common.MapToListTypeAdapter;
import io.leangen.graphql.generator.mapping.strategy.AnnotatedInterfaceStrategy;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

@Configuration
@Import(value = {ApplicationConfiguration.class})
@RequiredArgsConstructor
public class SchemaConfig {

    private final Query query;
//    private final Mutation mutation;
    private final ObjectMapper objectMapper;

    @Bean
    @Lazy
    public GraphQL graphQL() {
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withOperationsFromSingleton(query)
//                .withOperationsFromSingleton(mutation)
                .withValueMapperFactory(JacksonValueMapperFactory.builder().withPrototype(objectMapper).build())
                .withInterfaceMappingStrategy(new AnnotatedInterfaceStrategy(true))
                .withTypeMappers(new CustomEnumMapper(), new MapToListTypeAdapter())
                .generate();

//        final AsyncExecutionStrategy strategy = new AsyncExecutionStrategy(new CustomDataFetcherExceptionHandler());
        return GraphQL.newGraphQL(schema)
//                .queryExecutionStrategy(strategy)
//                .mutationExecutionStrategy(strategy)
                .build();
    }

}
