package com.mrmessy.messenger.graphql.types;

import com.mrmessy.messenger.interfaces.INameable;
import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLScalarType;
import lombok.extern.log4j.Log4j2;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log4j2
public class GraphQLCustomEnumType<E extends Enum<E> & INameable> {

    public GraphQLCustomEnumType(Class<E> clazz) {
        // todo: fix deprecated
//        newObject().name(clazz.getSimpleName()).fields(fieldDefinitions(clazz)).build();
//        super(clazz.getSimpleName(), "", fieldDefinitions(clazz), emptyList());
    }

    private static List<GraphQLFieldDefinition> fieldDefinitions(Class clazz) {
        List<GraphQLFieldDefinition> definitions = new ArrayList<>(Arrays.asList(
                GraphQLFieldDefinition.newFieldDefinition()
                        .name("id")
                        .dataFetcher(env -> ((INameable) env.getSource()).getId())
                        .type(Scalars.GraphQLInt)
                        .build(),
                GraphQLFieldDefinition.newFieldDefinition()
                        .name("name")
                        .dataFetcher(env -> ((INameable) env.getSource()).getName())
                        .type(Scalars.GraphQLString)
                        .build()));
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                if (!"id".equals(descriptor.getName()) && !"name".equals(descriptor.getName())) {
                    getScalar(descriptor.getPropertyType()).ifPresent(scalar -> {
                        Method getter = descriptor.getReadMethod();
                        definitions.add(
                                GraphQLFieldDefinition.newFieldDefinition()
                                        .name(descriptor.getName())
                                        .dataFetcher(new EnumDataFetcher(clazz, getter))
                                        .type(scalar)
                                        .build());
                    });
                }
            }
        } catch (Exception e) {
            System.err.println(String.format("Failed to create field definition for %s", clazz.getSimpleName()));
        }
        return definitions;
    }

    private static <T> Optional<GraphQLScalarType> getScalar(T clazz) {
        if (String.class.getCanonicalName().equals(((Class) clazz).getCanonicalName())) {
            return Optional.of(Scalars.GraphQLString);
        }
        if (Integer.class.getCanonicalName().equals(((Class) clazz).getCanonicalName())) {
            return Optional.of(Scalars.GraphQLInt);
        }
        if (Boolean.class.getCanonicalName().equals(((Class) clazz).getCanonicalName())) {
            return Optional.of(Scalars.GraphQLBoolean);
        }
        return Optional.empty();
    }

    private static class EnumDataFetcher<E> implements DataFetcher {
        private final Class<E> clazz;
        private final Method method;

        EnumDataFetcher(Class<E> clazz, Method method) {
            this.clazz = clazz;
            this.method = method;
        }

        @Override
        public Object get(DataFetchingEnvironment environment) {
            Object source = environment.getSource();
            try {
                return method.invoke(source);
            } catch (Exception e) {
                System.err.println(String.format("Failed to invoke method %s of %s", method.getName(), clazz.getSimpleName()));
//                log.error(String.format("Failed to invoke method %s of %s", method.getName(), clazz.getSimpleName()), e);
                return null;
            }
        }
    }
}
