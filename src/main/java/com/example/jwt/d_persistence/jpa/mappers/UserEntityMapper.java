package com.example.jwt.d_persistence.jpa.mappers;

import java.util.UUID;

import com.example.jwt.c_domain.models.User;
import com.example.jwt.d_persistence.jpa.models.UserEntity;

public class UserEntityMapper {

    public static User toUser(UserEntity userEntity) {
        if (userEntity == null)
            return null;

        User user = new User(
                UUID.fromString(userEntity.getId()),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPasswordHash());

        return user;
    }

    public static UserEntity toUserEntity(User user) {
        if (user == null)
            return null;

        UserEntity userEntity = new UserEntity(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash());

        return userEntity;
    }
}