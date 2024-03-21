package com.chatbots.app.services.impl;

import com.chatbots.app.models.entities.AllowedOrigins;
import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.models.entities.Subscription;
import com.chatbots.app.models.entities.User;
import com.chatbots.app.repositories.ChatBotRepository;
import com.chatbots.app.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ChatBotServiceImplTest {

    @Mock
    private ChatBotRepository chatBotRepository;


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChatBotServiceImpl chatBotService;

    @Test
    void createChatBot_UserNotFound() {
        User user = new User();
        user.setId(1);
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> chatBotService.createChatBot(new ChatBot(), user));
    }

    @Test
    void createChatBot_MaxChatBotsReached() {
        User user = new User();
        user.setId(1);
        Subscription subscription = new Subscription();
        subscription.setChatBotsLimit(1);
        user.setSubscription(subscription);
        ChatBot existingChatBot = new ChatBot();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(chatBotRepository.findByUser_IdAndDeletedIs(user.getId(), false)).thenReturn(List.of(existingChatBot, new ChatBot()));

        Exception exception = assertThrows(RuntimeException.class, () -> chatBotService.createChatBot(new ChatBot(), user));
        assertEquals("You have reached the maximum number of chatbots allowed", exception.getMessage());
    }

    @Test
    void createChatBot_Success() {
        User user = new User();
        user.setId(1);
        Subscription subscription = new Subscription();
        subscription.setChatBotsLimit(10);
        user.setSubscription(subscription);
        ChatBot chatBot = new ChatBot();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(chatBotRepository.findByUser_IdAndDeletedIs(user.getId(), false)).thenReturn(List.of());
        when(chatBotRepository.save(any(ChatBot.class))).thenAnswer(i -> i.getArgument(0));

        ChatBot createdChatBot = chatBotService.createChatBot(chatBot, user);

        assertNotNull(createdChatBot);
        assertEquals(user, createdChatBot.getUser());
        assertFalse(createdChatBot.getDeleted());
    }
    @Test
    void updateChatBot_ChatBotNotFound() {
        ChatBot chatBot = new ChatBot();
        chatBot.setId(UUID.randomUUID());
        when(chatBotRepository.findByIdAndDeletedIs(chatBot.getId(), false)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> chatBotService.updateChatBot(chatBot));
        assertEquals("ChatBot not found", exception.getMessage());
    }

    @Test
    void updateChatBot_Success() {
        ChatBot existingChatBot = new ChatBot();
        existingChatBot.setId(UUID.randomUUID());
        existingChatBot.setName("Existing ChatBot");

        ChatBot updatedInfo = new ChatBot();
        updatedInfo.setId(existingChatBot.getId());
        updatedInfo.setName("Updated Name");

        when(chatBotRepository.findByIdAndDeletedIs(updatedInfo.getId(), false)).thenReturn(Optional.of(existingChatBot));
        when(chatBotRepository.save(any(ChatBot.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ChatBot updatedChatBot = chatBotService.updateChatBot(updatedInfo);

        assertNotNull(updatedChatBot);
        assertEquals("Updated Name", updatedChatBot.getName());
    }
    @Test
    void validateOrigin_Valid() {
        String encodedOrigin = Base64.getEncoder().encodeToString("http://valid-origin.com".getBytes());
        ChatBot chatBot = new ChatBot();
        chatBot.setAllowedOrigins(List.of(AllowedOrigins.builder().origin("http://valid-origin.com").build()));

        assertTrue(chatBotService.validateOrigin(encodedOrigin, chatBot));
    }

    @Test
    void validateOrigin_Invalid() {
        String encodedOrigin = Base64.getEncoder().encodeToString("http://invalid-origin.com".getBytes());
        ChatBot chatBot = new ChatBot();
        chatBot.setAllowedOrigins(List.of(AllowedOrigins.builder().origin("http://valid-origin.com").build()));

        assertFalse(chatBotService.validateOrigin(encodedOrigin, chatBot));
    }
    @Test
    void getMyChatBots_Success() {
        Integer userId = 1;
        ChatBot chatBot = new ChatBot();
        when(chatBotRepository.findByUser_IdAndDeletedIs(userId, false)).thenReturn(List.of(chatBot));

        List<ChatBot> chatBots = chatBotService.getMyChatBots(userId);

        assertFalse(chatBots.isEmpty());
        assertEquals(1, chatBots.size());
    }
    @Test
    void getChatBotById_NotFound() {
        UUID chatBotId = UUID.randomUUID();
        when(chatBotRepository.findByIdAndDeletedIs(chatBotId, false)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> chatBotService.getChatBotById(chatBotId, false, ""));
        assertEquals("ChatBot not found", exception.getMessage());
    }

    @Test
    void getChatBotById_OriginNotAllowed() {
        UUID chatBotId = UUID.randomUUID();
        ChatBot chatBot = new ChatBot();
        chatBot.setAllowedOrigins(List.of(AllowedOrigins.builder().origin("http://valid-origin.com").build()));
        when(chatBotRepository.findByIdAndDeletedIs(chatBotId, false)).thenReturn(Optional.of(chatBot));

        Exception exception = assertThrows(RuntimeException.class, () -> chatBotService.getChatBotById(chatBotId, true, Base64.getEncoder().encodeToString("http://invalid-origin.com".getBytes())));
        assertEquals("Origin not allowed", exception.getMessage());
    }
    @Test
    void deleteChatBot_Success() {
        UUID chatbotId = UUID.randomUUID();
        doNothing().when(chatBotRepository).deleteById(chatbotId);

        assertDoesNotThrow(() -> chatBotService.deleteChatBot(chatbotId));
        verify(chatBotRepository, times(1)).deleteById(chatbotId);
    }
}