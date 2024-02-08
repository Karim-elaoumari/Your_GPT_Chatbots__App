package com.chatbots.security.services;

import com.chatbots.security.models.dto.UserResponseDTO;
import com.chatbots.security.models.entities.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Integer userId);
    RefreshToken verifyExpiration(RefreshToken token);
    UserResponseDTO generateNewToken(String token);
    void deleteRefreshToken(String token);
    void revokeRefreshToken(String token);

}
