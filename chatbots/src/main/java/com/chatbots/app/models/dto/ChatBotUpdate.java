package com.chatbots.app.models.dto;

import com.chatbots.app.models.entities.ChatBot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ChatBotUpdate(
        @NotNull
        @org.hibernate.validator.constraints.UUID
        String id,
        @NotNull @NotBlank
        String name,
        @NotNull @NotBlank
        String instructions,
        @NotNull @NotBlank
        String logoUrl,
        @NotNull @NotBlank
        String initialMessage,
        @NotNull @NotBlank
        String botMessageColor,
        @NotNull @NotBlank
        String userMessageColor,
        @NotNull @NotBlank
        String headerColor,
        @NotNull @NotBlank
        String buttonBackgroundColor,
        @NotNull @NotBlank
        String chatBackgroundColor,
        @NotNull @NotBlank
        String messageBackgroundColor,
        @NotNull @NotBlank
        String textColor
) {
        public ChatBot toChatBot() {
                return ChatBot.builder()
                        .id(UUID.fromString(id))
                        .name(name)
                        .instructions(instructions)
                        .logoUrl(logoUrl)
                        .initialMessage(initialMessage)
                        .botMessageColor(botMessageColor)
                        .userMessageColor(userMessageColor)
                        .headerColor(headerColor)
                        .buttonBackgroundColor(buttonBackgroundColor)
                        .chatBackgroundColor(chatBackgroundColor)
                        .messageBackgroundColor(messageBackgroundColor)
                        .textColor(textColor)
                        .build();
        }
}
