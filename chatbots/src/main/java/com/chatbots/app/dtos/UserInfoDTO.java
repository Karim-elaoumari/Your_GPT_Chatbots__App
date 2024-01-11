package com.chatbots.app.dtos;

public record UserInfoDTO(
        Long id,
        String sub,
        String name,
        String picture,
        String email,
        String role
) { }
