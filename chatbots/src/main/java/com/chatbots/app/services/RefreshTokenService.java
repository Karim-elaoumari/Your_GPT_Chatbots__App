package com.chatbots.app.services;

import com.chatbots.app.models.dto.UserResponseDTO;
import com.chatbots.app.models.entities.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Integer userId);
    RefreshToken verifyExpiration(RefreshToken token);
    UserResponseDTO generateNewToken(String token);
    void deleteRefreshToken(String token);
    void revokeRefreshToken(String token);

}
