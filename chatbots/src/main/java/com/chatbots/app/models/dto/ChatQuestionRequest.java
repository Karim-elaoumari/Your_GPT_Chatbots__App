package com.chatbots.app.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChatQuestionRequest(
        @NotNull @NotBlank
        String question,
        @Pattern(regexp = "^[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{12}$", message = "Invalid UUID")
        String chatBotId,
        @NotNull @NotBlank
        String userCode
) {
}
