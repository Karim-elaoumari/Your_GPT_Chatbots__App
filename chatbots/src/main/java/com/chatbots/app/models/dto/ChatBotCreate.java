package com.chatbots.app.models.dto;

import com.chatbots.app.models.entities.ChatBot;

public record ChatBotCreate(
        String name
) {
    public ChatBot toChatBot() {
        return ChatBot.builder()
                .name(name)
                .build();
    }
}
