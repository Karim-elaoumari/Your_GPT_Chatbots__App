package com.chatbots.app.services;

import com.chatbots.app.models.entities.AllowedOrigins;

import java.util.List;
import java.util.UUID;

public interface OriginsService {

    AllowedOrigins addChatBotAllowedOrigin(UUID chatBotId, String origin);
    Boolean removeChatBotAllowedOrigin(UUID chatBotId,String origin);
}
