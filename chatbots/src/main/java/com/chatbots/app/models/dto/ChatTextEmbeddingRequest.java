package com.chatbots.app.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.UUID;

public record ChatTextEmbeddingRequest(
        @NotBlank
        @NotNull
        String text,
        @NotBlank
        @UUID
        String chatBotId
) {
}
