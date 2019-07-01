package com.mrmessy.messenger.enums.base;

import com.mrmessy.messenger.interfaces.Identifiable;
import com.mrmessy.messenger.utils.ConverterUtils;

import javax.persistence.AttributeConverter;

public abstract class BaseEnumConverter<E extends Enum<E> & Identifiable> implements AttributeConverter<E, Integer> {

    public abstract Class<E> getClazz();

    @Override
    public Integer convertToDatabaseColumn(E attr) {
        return (attr == null) ? null : attr.getId();
    }

    @Override
    public E convertToEntityAttribute(Integer dbData) {
        return (dbData == null) ? null : ConverterUtils.findById(getClazz().getEnumConstants(), dbData);
    }
}
