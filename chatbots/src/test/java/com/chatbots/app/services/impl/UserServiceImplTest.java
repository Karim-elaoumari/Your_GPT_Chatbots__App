package com.chatbots.app.services.impl;

import com.chatbots.app.models.entities.User;
import com.chatbots.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void save() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("Test User");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User savedUser = userService.save(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("Test User", savedUser.getFirstName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findUserByEmail() {
        // Arrange
        String email = "test@example.com";
        Optional<User> optionalUser = Optional.of(new User());
        optionalUser.get().setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(optionalUser);

        // Act
        Optional<User> foundUser = userService.findUserByEmail(email);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }
}