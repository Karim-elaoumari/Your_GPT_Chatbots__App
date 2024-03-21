package com.chatbots.app.services.impl;

import com.chatbots.app.SecurityExceptionsHandlers.exceptions.TokenException;
import com.chatbots.app.models.dto.UserResponseDTO;
import com.chatbots.app.models.entities.RefreshToken;
import com.chatbots.app.models.entities.User;
import com.chatbots.app.repositories.RefreshTokenRepository;
import com.chatbots.app.repositories.UserRepository;
import com.chatbots.app.services.JwtService;
import com.chatbots.app.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Value("${application.token.signing.refresh-token-expiration}")
    private long refreshExpiration;

    @Override
    public RefreshToken createRefreshToken(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        RefreshToken refreshToken = RefreshToken.builder()
                .revoked(false)
                .user(user)
                .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token == null){
            throw new TokenException(null, "Token is null");
        }
        if(token.getExpiryDate().compareTo(Instant.now()) < 0 ){
            refreshTokenRepository.delete(token);
            throw new TokenException(token.getToken(), "Refresh token was expired. Please make a new authentication request");
        }
        return token;
    }

    @Override
    public UserResponseDTO generateNewToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findById(refreshToken)
                .map(this::verifyExpiration)
                .orElseThrow(() -> new TokenException(refreshToken,"Refresh token does not exist"));
        if(token.isRevoked()){
            throw new TokenException(refreshToken,"Refresh token was revoked");
        }
        User user = token.getUser();
        String jwtToken = jwtService.generateToken(user);
        return UserResponseDTO.fromUser(user,jwtToken,refreshToken);



    }
    @Override
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteById(token);
    }
    @Override
    public void revokeRefreshToken(String token) {
        refreshTokenRepository.findById(token)
                .map(this::verifyExpiration)
                .map(refreshToken -> {
                    refreshToken.setRevoked(true);
                    return refreshTokenRepository.save(refreshToken);
                })
                .orElseThrow(() -> new TokenException(token,"Refresh token does not exist"));
    }

}
