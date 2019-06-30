package com.mrmessy.messenger.enums;

import com.mrmessy.messenger.enums.base.BaseEnumConverter;
import com.mrmessy.messenger.interfaces.INameable;
import com.mrmessy.messenger.utils.ConverterUtils;
import lombok.Getter;

import javax.persistence.Converter;


@Getter
public enum UserRole implements INameable {
    ADMIN(0, "Admin"),
    User(1, "User");

    private final Integer id;
    private final String name;

    UserRole(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static UserRole getById(Integer id) {
        return ConverterUtils.findById(values(), id);
    }

    @Converter(autoApply = true)
    public static class UserRoleConverter extends BaseEnumConverter<UserRole> {

        @Override
        public Class<UserRole> getClazz() {
            return UserRole.class;
        }
    }
}
