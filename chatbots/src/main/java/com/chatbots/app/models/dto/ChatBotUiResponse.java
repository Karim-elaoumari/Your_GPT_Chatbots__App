package com.chatbots.app.models.dto;


import com.chatbots.app.models.entities.ChatBot;

public record ChatBotUiResponse(
        String id,
        String name,
        String logoUrl,
        String initialMessage,
        String headerColor,
        String buttonBackgroundColor,
        String botMessageColor,
        String userMessageColor,
        String chatBackgroundColor,
        String messageBackgroundColor,
        String textColor
) {
    public static ChatBotUiResponse fromChatBot(ChatBot chatBot) {
        return new ChatBotUiResponse(
                chatBot.getId().toString(),
                chatBot.getName(),
                chatBot.getLogoUrl(),
                chatBot.getInitialMessage(),
                chatBot.getHeaderColor(),
                chatBot.getButtonBackgroundColor(),
                chatBot.getBotMessageColor(),
                chatBot.getUserMessageColor(),
                chatBot.getChatBackgroundColor(),
                chatBot.getMessageBackgroundColor(),
                chatBot.getTextColor()
        );
    }
}
