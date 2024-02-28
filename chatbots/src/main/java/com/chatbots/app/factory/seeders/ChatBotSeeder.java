package com.chatbots.app.factory.seeders;

import com.chatbots.app.factory.fakers.ChatBotFaker;
import com.chatbots.app.factory.fakers.UserFaker;
import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.models.entities.User;
import com.chatbots.app.repositories.ChatBotRepository;
import com.chatbots.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatBotSeeder {
    private final ChatBotFaker chatBotFaker;
    private final UserFaker userFaker;
    private final ChatBotRepository chatBotRepository;
    private final UserRepository userRepository;

    public void seed() {
        ChatBot chatBot = chatBotFaker.makeChatBot();
        User user = userFaker.makeUser();
        user = userRepository.save(user);
        chatBot.setUser(user);
        chatBotRepository.save(chatBot);

    }


}
