package com.chatbots.app.services;

import com.chatbots.app.models.dto.ChatQuestionRequest;
import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.models.entities.User;

import java.util.List;
import java.util.UUID;

public interface ChatBotService {

    String getResponse(ChatQuestionRequest request);
    ChatBot createChatBot(ChatBot chatBot, User user);
    ChatBot updateChatBot(ChatBot chatBot);
    List<ChatBot> getMyChatBots(Integer userId);
    ChatBot getChatBotById(UUID chatBotId,Boolean validateOrigin,String origin);
    Boolean validateOrigin(String origin, ChatBot chatBot);
    void deleteChatBot(UUID chatbotId);

}
