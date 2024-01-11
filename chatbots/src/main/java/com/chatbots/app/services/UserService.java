package com.chatbots.app.services;

import com.chatbots.app.models.entities.UserEntity;

import java.util.Optional;

public interface UserService {
    UserEntity save(UserEntity userEntity);
    Optional<UserEntity> findUserByEmail(String email);
}
