package com.chatbots.app.models.dto;

import com.chatbots.app.models.entities.ChatBot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record ChatBotUpdate(
        @NotNull
        @Pattern(regexp = "^[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{12}$", message = "Invalid UUID")
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
        String headerTitle,
        @NotNull @NotBlank
        String headerColor,
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
                        .headerTitle(headerTitle)
                        .headerColor(headerColor)
                        .chatBackgroundColor(chatBackgroundColor)
                        .messageBackgroundColor(messageBackgroundColor)
                        .textColor(textColor)
                        .build();
        }
}
