package com.mrmessy.messenger.graphql.types;

import com.mrmessy.messenger.interfaces.INameable;
import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLScalarType;
import lombok.extern.log4j.Log4j2;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static graphql.schema.GraphQLObjectType.newObject;

@Log4j2
public class GraphQLCustomEnumType<E extends Enum<E> & INameable> {

    public static GraphQLObjectType createGraphQLCustomEnumType(Class<?> clazz) {
        return newObject().name(clazz.getSimpleName()).fields(fieldDefinitions(clazz)).build();
    }

    private static List<GraphQLFieldDefinition> fieldDefinitions(Class clazz) {
        List<GraphQLFieldDefinition> definitions = new ArrayList<>(Arrays.asList(
                GraphQLFieldDefinition.newFieldDefinition()
                        .name("id")
                        .type(Scalars.GraphQLInt)
                        .build(),
                GraphQLFieldDefinition.newFieldDefinition()
                        .name("name")
                        .type(Scalars.GraphQLString)
                        .build()));
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                if (!"id".equals(descriptor.getName()) && !"name".equals(descriptor.getName())) {
                    getScalar(descriptor.getPropertyType()).ifPresent(scalar -> {
                        definitions.add(
                                GraphQLFieldDefinition.newFieldDefinition()
                                        .name(descriptor.getName())
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
}
