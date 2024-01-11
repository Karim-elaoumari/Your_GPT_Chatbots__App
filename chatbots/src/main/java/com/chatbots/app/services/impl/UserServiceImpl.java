package com.chatbots.app.services.impl;

import com.chatbots.app.models.entities.UserEntity;
import com.chatbots.app.repositories.UserRepository;
import com.chatbots.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
