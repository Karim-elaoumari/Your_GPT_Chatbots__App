package com.chatbots.app.services;

import com.chatbots.app.models.dto.UserLoginRequestDTO;
import com.chatbots.app.models.dto.UserResponseDTO;
import com.chatbots.app.models.entities.User;

public interface AuthenticationService {
    UserResponseDTO login(UserLoginRequestDTO userLoginRequestDTO);
    UserResponseDTO register(User user);
    void  logout(String token);
}
