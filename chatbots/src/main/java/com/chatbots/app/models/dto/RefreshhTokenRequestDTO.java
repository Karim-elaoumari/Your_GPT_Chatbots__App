package com.chatbots.app.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RefreshhTokenRequestDTO {
    @JsonProperty("refresh_token")
    @NotBlank(message = "Refresh token cannot be blank")
    private String refreshToken;
}
