package com.chatbots.app.services;

import com.chatbots.app.models.dto.ChatQuestionRequest;
import com.chatbots.app.models.entities.ChatBot;

import java.util.List;
import java.util.UUID;

public interface ChatBotService {

    String getResponse(ChatQuestionRequest request,String origin);
    ChatBot createChatBot(ChatBot chatBot);
    ChatBot updateChatBot(ChatBot chatBot);
    List<ChatBot> getMyChatBots(Integer userId);
}
