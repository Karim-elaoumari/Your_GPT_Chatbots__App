package com.chatbots.app.services.impl;

import com.chatbots.app.models.entities.AllowedOrigins;
import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.repositories.AlloweOriginsRepository;
import com.chatbots.app.services.ChatBotService;
import com.chatbots.app.services.OriginsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class OriginsServiceImpl implements OriginsService{
    private final ChatBotService chatBotService;
    private final  AlloweOriginsRepository alloweOriginsRepository;
    @Override
    public AllowedOrigins addChatBotAllowedOrigin(UUID chatBotId, String origin) {
        ChatBot chatBot = chatBotService.getChatBotById(chatBotId,false,null);
        AllowedOrigins allowedOrigins = new AllowedOrigins();
        allowedOrigins.setChatBot(chatBot);
        allowedOrigins.setOrigin(origin);
        return alloweOriginsRepository.save(allowedOrigins);
    }
    @Override
    public Boolean removeChatBotAllowedOrigin(UUID chatBotId, String origin) {
        ChatBot chatBot = chatBotService.getChatBotById(chatBotId,false,null);
        AllowedOrigins allowedOrigins = chatBot.getAllowedOrigins().stream().filter(allowedOrigin -> allowedOrigin.getOrigin().equals(origin)).findFirst().orElse(null);
        if(allowedOrigins!=null){
            alloweOriginsRepository.delete(allowedOrigins);
            return true;
        }
        return false;
    }
}
