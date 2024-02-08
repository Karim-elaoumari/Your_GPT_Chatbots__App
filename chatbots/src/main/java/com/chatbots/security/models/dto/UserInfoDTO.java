package com.chatbots.security.models.dto;

public record UserInfoDTO(
        Long id,
        String sub,
        String name,
        String picture,
        String email,
        String role
) { }
