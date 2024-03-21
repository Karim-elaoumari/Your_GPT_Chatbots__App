package com.chatbots.app.services.impl;

import com.chatbots.app.SecurityExceptionsHandlers.exceptions.TokenException;
import com.chatbots.app.models.entities.RefreshToken;
import com.chatbots.app.models.entities.User;
import com.chatbots.app.repositories.RefreshTokenRepository;
import com.chatbots.app.repositories.UserRepository;
import com.chatbots.app.services.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;

    @Value("${application.token.signing.refresh-token-expiration}")
    private long refreshExpiration;

    // Helper method to create a mock User
    private User mockUser(Integer userId) {
        User user = new User();
        // Set necessary fields, assuming a constructor or setters are available
        user.setId(userId);
        return user;
    }

    // Helper method to create a mock RefreshToken
    private RefreshToken mockRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .revoked(false)
                .user(user)
                .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .build();
        return refreshToken;
    }
    @Test
    void createRefreshToken_Success() {
        Integer userId = 1;
        User user = mockUser(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);

        assertNotNull(refreshToken);
        assertEquals(user, refreshToken.getUser());
        assertFalse(refreshToken.isRevoked());
        assertNotNull(refreshToken.getToken());
    }
    @Test
    void createRefreshToken_UserNotFound_ThrowsException() {
        Integer userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> refreshTokenService.createRefreshToken(userId));
    }
    @Test
    void verifyExpiration_NotExpired_Success() {
        User user = mockUser(1);
        RefreshToken refreshToken = mockRefreshToken(user);

        RefreshToken verifiedToken = refreshTokenService.verifyExpiration(refreshToken);

        assertEquals(refreshToken, verifiedToken);
    }
    @Test
    void verifyExpiration_Expired_ThrowsException() {
        User user = mockUser(1);
        RefreshToken refreshToken = mockRefreshToken(user);
        refreshToken.setExpiryDate(Instant.now().minusSeconds(1)); // Expired token

        assertThrows(TokenException.class, () -> refreshTokenService.verifyExpiration(refreshToken));
    }

    @Test
    void generateNewToken_TokenDoesNotExist_ThrowsException() {
        String refreshTokenString = UUID.randomUUID().toString();
        when(refreshTokenRepository.findById(refreshTokenString)).thenReturn(Optional.empty());

        assertThrows(TokenException.class, () -> refreshTokenService.generateNewToken(refreshTokenString));
    }


    @Test
    void revokeRefreshToken_Success() {
        // Arrange
        String token = "existingToken";
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(Instant.now().plusMillis(3600000)); // Example: 1 hour ahead
        refreshToken.setRevoked(false);

        when(refreshTokenRepository.findById(token)).thenReturn(Optional.of(refreshToken));
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        refreshTokenService.revokeRefreshToken(token);

        // Assert
        assertTrue(refreshToken.isRevoked());
        verify(refreshTokenRepository).save(refreshToken);
    }


}