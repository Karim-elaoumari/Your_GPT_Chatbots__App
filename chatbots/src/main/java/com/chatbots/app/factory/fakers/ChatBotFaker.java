package com.chatbots.app.factory.fakers;

import com.chatbots.app.models.entities.ChatBot;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class ChatBotFaker {
    private final Faker faker = new Faker();
    public ChatBot makeChatBot(){
        return ChatBot.builder()
                .name("ChatyBot Assistant")
                .instructions("I want you to act as a support agent. Your name is \"AI Assistant\". You will provide me with answers using the given data. If the answer can't be generated using the given data, say I am not sure. and stop after that. Refuse to answer any question not about (the info or your name,your job,hello word). Never break character.")
                .chatBackgroundColor("#f3f6f4")
                .messageBackgroundColor("#ffffff")
                .buttonBackgroundColor("#4f46e5")
                .userMessageColor("#E5EDFF")
                .botMessageColor("#FFFFFF")
                .headerColor("#ffffff")
                .deleted(false)
                .logoUrl("https://img.freepik.com/free-vector/chatbot-chat-message-vectorart_78370-4104.jpg")
                .initialMessage("Hello, I am AI Assistant. I am here to help you with the information you need. \n feel free to ask me anything.")
                .textColor("#362e2e")
                .build();
    }
}
