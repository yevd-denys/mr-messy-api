package com.mrmessy.messenger.mappers;

import com.mrmessy.messenger.entities.User;
import com.mrmessy.messenger.enums.UserRole;
import com.mrmessy.messenger.graphql.dto.UserMutationDto;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

public class UserMapper {
    public static User convert(UserMutationDto dto) {
        var user = new User();
        user.setCreatedAt(LocalDateTime.now());
        return convert(user, dto);
    }

    public static User convert(User user, UserMutationDto dto) {
        if (nonNull(dto.getEmail())) {
            user.setEmail(dto.getEmail());
        }

        if (nonNull(dto.getFirstName())) {
            user.setFirstName(dto.getFirstName());
        }

        if (nonNull(dto.getLastName())) {
            user.setLastName(dto.getLastName());
        }

        if (nonNull(dto.getPassword())) {
            user.setPassword(dto.getPassword());
        }

        if (nonNull(dto.getRole())) {
            user.setRole(UserRole.getById(dto.getRole()));
        }

        user.setName(user.getFirstName() + " " + user.getLastName());
        user.setUpdatedAt(LocalDateTime.now());

        return user;
    }
}
