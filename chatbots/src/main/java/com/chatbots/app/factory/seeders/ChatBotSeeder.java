package com.chatbots.app.factory.seeders;

import com.chatbots.app.factory.fakers.ChatBotFaker;
import com.chatbots.app.factory.fakers.UserFaker;
import com.chatbots.app.models.entities.*;
import com.chatbots.app.models.enums.UserRole;
import com.chatbots.app.repositories.*;
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
    private final AlloweOriginsRepository alloweOriginsRepository;
    private final SubscriptionRepository subscriptionRepository;

    public void seed() {
        ChatBot chatBot = chatBotFaker.makeChatBot();
        User user = userFaker.makeUser();
        user.setEmail("elaoumarikarim@gmail.com");
        user.setSubscription(getFreeSubscription());
        Role role = roleRepository.findByName(UserRole.ADMIN).get();
        user.setRole(role);
        user = userRepository.save(user);
        chatBot.setUser(user);
        chatBot = chatBotRepository.save(chatBot);
        AllowedOrigins allowedOrigins = AllowedOrigins.builder()
                .origin("localhost:53247")
                .chatBot(chatBot)
                .build();
        alloweOriginsRepository.save(allowedOrigins);
    }
    private Subscription getFreeSubscription(){
        return subscriptionRepository.findByName("Free Subscription").orElse(null);
    }


}
