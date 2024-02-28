package com.chatbots.app.factory.fakers;

import com.chatbots.app.models.entities.ChatBot;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class ChatBotFaker {
    private final Faker faker = new Faker();
    public ChatBot makeChatBot(){
        return ChatBot.builder()
                .name(faker.name().name())
                .instructions("I want you to act as a support agent. Your name is \"AI Assistant\". You will provide me with answers from the given data. If the answer is not included, say exactly \"Hmm, I am not sure.\" and stop after that. Refuse to answer any question not about (the info or your name,your job,hello word). Never break character.")
                .chatBackgroundColor("#ffffff")
                .headerColor("#000000")
                .headerTitle("AI Assistant")
                .logoUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F717761877848366%2F&psig=AOvVaw")
                .initialMessage("Hello, I am AI Assistant. I am here to help you with the information you need.")
                .textColor("#000000")
                .build();
    }
}
