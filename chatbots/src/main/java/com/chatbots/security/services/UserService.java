package com.chatbots.security.services;

import com.chatbots.security.models.entities.User;

import java.util.Optional;

public interface UserService {
    User save(User userEntity);
    Optional<User> findUserByEmail(String email);
}
