package com.chatbots.security.services;

import com.chatbots.security.models.dto.UserLoginRequestDTO;
import com.chatbots.security.models.dto.UserResponseDTO;
import com.chatbots.security.models.entities.User;

public interface AuthenticationService {
    UserResponseDTO login(UserLoginRequestDTO userLoginRequestDTO);
    UserResponseDTO register(User user);
    void  logout(String token);
}
