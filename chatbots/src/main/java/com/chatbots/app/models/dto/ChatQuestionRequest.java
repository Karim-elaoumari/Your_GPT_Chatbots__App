package com.chatbots.app.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.UUID;

public record ChatQuestionRequest(
        @NotNull @NotBlank
        String question,
        @UUID
        String chatBotId,
        @NotNull @NotBlank
        String userCode,
        @NotNull @NotBlank
        String origin
) {
}
