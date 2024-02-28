package com.chatbots.app.services;

import com.chatbots.app.models.entities.User;

import java.util.Optional;

public interface UserService {
    User save(User userEntity);
    Optional<User> findUserByEmail(String email);
}
