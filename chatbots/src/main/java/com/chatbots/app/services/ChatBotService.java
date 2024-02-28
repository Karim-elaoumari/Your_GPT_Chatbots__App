package com.chatbots.app.services;

import java.util.UUID;

public interface ChatBotService {

    String getResponse(String question,String userCode, UUID chatBotId);
}
