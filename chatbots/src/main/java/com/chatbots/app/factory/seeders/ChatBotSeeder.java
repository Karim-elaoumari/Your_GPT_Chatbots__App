package com.chatbots.app.factory.seeders;

import com.chatbots.app.factory.fakers.ChatBotFaker;
import com.chatbots.app.factory.fakers.UserFaker;
import com.chatbots.app.models.entities.ChatBot;
import com.chatbots.app.models.entities.Role;
import com.chatbots.app.models.entities.User;
import com.chatbots.app.models.enums.UserRole;
import com.chatbots.app.repositories.ChatBotRepository;
import com.chatbots.app.repositories.RoleRepository;
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
    private final RoleRepository roleRepository;

    public void seed() {
        ChatBot chatBot = chatBotFaker.makeChatBot();
        User user = userFaker.makeUser();
        user.setEmail("elaoumarikarim@gmail.com");
        Role role = roleRepository.findByName(UserRole.ADMIN).get();
        user.setRole(role);
        user = userRepository.save(user);
        chatBot.setUser(user);
        chatBotRepository.save(chatBot);

    }


}
