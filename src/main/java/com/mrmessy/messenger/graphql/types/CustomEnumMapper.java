package com.mrmessy.messenger.graphql.types;

import com.mrmessy.messenger.interfaces.Identifiable;
import com.mrmessy.messenger.utils.ConverterUtils;
import graphql.language.IntValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.generator.BuildContext;
import io.leangen.graphql.generator.OperationMapper;
import io.leangen.graphql.generator.mapping.TypeMapper;
import io.leangen.graphql.util.ClassUtils;

import java.lang.reflect.AnnotatedType;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.mrmessy.messenger.graphql.types.GraphQLCustomEnumType.createGraphQLCustomEnumType;
import static graphql.schema.GraphQLScalarType.newScalar;

public class CustomEnumMapper implements TypeMapper {
    private final Map<String, GraphQLObjectType> typeGraphQLObjectTypeMap = new ConcurrentHashMap<>();
    private final Map<String, GraphQLInputType> inputTypeMap = new ConcurrentHashMap<>();

    @Override
    public GraphQLOutputType toGraphQLType(AnnotatedType javaType,
                                           OperationMapper operationMapper,
                                           Set<Class<? extends TypeMapper>> mappersToSkip,
                                           BuildContext buildContext) {
        Class<?> clazz = ClassUtils.getRawType(javaType.getType());
        String enumName = clazz.getSimpleName();
        return typeGraphQLObjectTypeMap.computeIfAbsent(enumName, s -> createGraphQLCustomEnumType(clazz));

    }

    @Override
    public GraphQLInputType toGraphQLInputType(AnnotatedType javaType,
                                               OperationMapper operationMapper,
                                               Set<Class<? extends TypeMapper>> mappersToSkip,
                                               BuildContext buildContext) {
        Class<?> clazz = ClassUtils.getRawType(javaType.getType());
        String enumName = "Input" + clazz.getSimpleName();
        return inputTypeMap.computeIfAbsent(enumName, s -> newScalar().name(enumName).coercing(new DataCoercing(clazz)).build());
    }

    @Override
    public boolean supports(AnnotatedType type) {
        return GenericTypeReflector.isSuperType(Identifiable.class, type.getType());
    }

    private static class DataCoercing<E extends Enum<E> & Identifiable> implements Coercing<E, Integer> {

        private final Class<E> clazz;

        private DataCoercing(Class<E> clazz) {
            this.clazz = clazz;
        }

        @Override
        public Integer serialize(Object input) {
            return ((Identifiable) input).getId();
        }

        @Override
        public E parseValue(Object input) {
            Integer id = 0;
            if (input instanceof IntValue) {
                id = ((IntValue) input).getValue().intValue();
            } else if (input instanceof Integer) {
                id = (Integer) input;
            }
            return ConverterUtils.findById(id, this.clazz);
        }

        @Override
        public E parseLiteral(Object input) {
            return parseValue(input);
        }
    }

}
